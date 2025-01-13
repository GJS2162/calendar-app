package com.example.calendarapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calendarapp.data.model.Event
import com.example.calendarapp.ui.presentation.screen.AddEventScreen
import com.example.calendarapp.ui.presentation.screen.CalendarScreen
import com.example.calendarapp.ui.presentation.screen.EventScreen
import com.example.calendarapp.ui.viewmodels.EventViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class Screen(val route: String) {
    data object CalendarScreen : Screen("calendar_screen")
    data object EventScreen : Screen("event_screen/{date}") {
        fun createRoute(date: String) = "event_screen/$date"
    }

    data object AddEventScreen : Screen("add_event_screen/{date}/{eventJson}") {
        fun createRoute(date: String, eventJson: String? = null) = "add_event_screen/$date/${eventJson ?: "{}"}"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.CalendarScreen.route
    ) {
        addCalendarScreen(navController)
        addEventScreen(navController)
        addAddEventScreen(navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.addCalendarScreen(navController: NavController) {
    composable(Screen.CalendarScreen.route) {
        val eventViewModel: EventViewModel = hiltViewModel()
        CalendarScreen(navController, eventViewModel)
    }
}

fun NavGraphBuilder.addAddEventScreen(navController: NavController) {
    composable(
        route = Screen.AddEventScreen.route,
        arguments = listOf(
            navArgument("date") { type = NavType.StringType },
            navArgument("eventJson") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val date = backStackEntry.arguments?.getString("date") ?: ""
        val eventJson = backStackEntry.arguments?.getString("eventJson") ?: ""

        val event: Event? = if (eventJson.isNotEmpty() && eventJson != "{}") {
            Json.decodeFromString<Event>(eventJson)
        } else {
            null
        }

        AddEventScreen(
            navController = navController,
            date = date,
            event = event
        )

    }
}

fun NavGraphBuilder.addEventScreen(navController: NavController) {
    composable(
        route = Screen.EventScreen.route,
        arguments = listOf(navArgument("date") { type = NavType.StringType })
    ) { backStackEntry ->
        val date = backStackEntry.arguments?.getString("date") ?: ""

        val eventViewModel: EventViewModel = hiltViewModel()
        EventScreen(
            navController = navController,
            eventViewModel = eventViewModel,
            date = date,
            onBack = { navController.popBackStack() },
            onEditEvent = { event ->
                val eventJson = Json.encodeToString(event)

                navController.navigate(Screen.AddEventScreen.createRoute(date, eventJson))
            },
            onDeleteEvent = { eventViewModel.deleteEvent(event = it) }
        )
    }
}
