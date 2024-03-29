package com.asabirov.diaryapp.presentation.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
//    scrollBehavior: TopAppBarScrollBehavior,
    onMenuClicked: () -> Unit,
//    dateIsSelected: Boolean,
//    onDateSelected: (ZonedDateTime) -> Unit,
//    onDateReset: () -> Unit,
) {
    val dateDialog = rememberSheetState()
    TopAppBar(
//        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Hamburger Menu Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        title = {
            Text(text = "Diary")
        },
        actions = {
//            if (dateIsSelected) {
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
//                }
//            } else {
//                IconButton(onClick = { dateDialog.show() }) {
//                    Icon(
//                        imageVector = Icons.Default.DateRange,
//                        contentDescription = "Date Icon",
//                        tint = MaterialTheme.colorScheme.onSurface
//                    )
//                }
            }
        })
}


//    )
//
//    CalendarDialog(
//        state = dateDialog,
//        selection = CalendarSelection.Date { localDate ->
//            onDateSelected(
//                ZonedDateTime.of(
//                    localDate,
//                    LocalTime.now(),
//                    ZoneId.systemDefault()
//                )
//            )
//        },
//        config = CalendarConfig(monthSelection = true, yearSelection = true)
//    )
//}