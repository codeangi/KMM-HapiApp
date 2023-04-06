package com.deepak.myapplication.android.feature.appointments

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deepak.myapplication.AppConstant.ADD_NOTES_DEFAULT_TEXT
import com.deepak.myapplication.android.MainActivityViewModel
import com.deepak.myapplication.android.R
import com.deepak.myapplication.android.theme.customCyan
import com.deepak.myapplication.android.theme.lightGrey
import com.deepak.myapplication.android.viewPort
import com.deepak.myapplication.model.BookingResource
import com.deepak.myapplication.model.Coding
import com.deepak.myapplication.model.ServiceType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppointmentReviewScreen(
    mainActivityViewModel: MainActivityViewModel,
    appointmentViewModel: AppointmentViewModel,
    isScheduleButtonRequired: Boolean = true,
    onScheduleAppointmentClick: () -> Unit,
    onBack: () -> Unit
) {
    val appointmentData = mainActivityViewModel.selectedAppointmentData

    val appointmentSuccessState by appointmentViewModel.appointmentBookingSuccess

    mainActivityViewModel.selectedAppointmentData.addedNotes?.let {
        mainActivityViewModel.addedNotesState.value = it
    }

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
            confirmStateChange = { false }
        )
    val coroutineScope = rememberCoroutineScope()

    if (appointmentSuccessState) {
        onScheduleAppointmentClick()
        appointmentViewModel.appointmentBookingSuccess.value = false
    }

    val isLoading by appointmentViewModel.isApiLoading.collectAsState()

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContent = {
            AddNotesBottomSheetUi(
                mainActivityViewModel
            ) {
                coroutineScope.launch {
                    bottomSheetState.hide()
                }
            }
        }) {

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Center
            ) {
                CircularProgressIndicator(
                    Modifier.size(48.dp)
                        .align(Center),
                    color = Color.Black
                )
            }
        }
        Column(modifier = Modifier.padding(viewPort)) {
            if (isScheduleButtonRequired.not()) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onBack.invoke()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = "Appointment Review",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.W600,
                color = Color.Black,
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "This time is reserved for you until 3:29 PM. Please complete scheduling by then.",
                style = MaterialTheme.typography.body1,
                color = Color.Black,
            )

            ReviewDataCardUi(
                "Provider",
                mainActivityViewModel.selectedCareTeamDoctor?.doctorName ?: "",
                R.drawable.outline_person_icon
            )
            ReviewDataCardUi(
                "Reason for visit",
                appointmentData.reason ?: "",
                R.drawable.outline_visit_reason
            )
            ReviewDataCardUi(
                "Location",
                appointmentData.appointmentLocationAddress ?: "",
                R.drawable.outline_location_icon
            )
            ReviewDataCardUi(
                "Date and time",
                appointmentData.timeSlotData ?: "",
                R.drawable.outline_date_icon
            )
            ReviewDataCardUi(
                "Notes",
                mainActivityViewModel.addedNotesState.value,
                R.drawable.outline_note_24,
                modifier = Modifier.clickable(isScheduleButtonRequired) {
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isScheduleButtonRequired) {
                Button(
                    onClick = {
                        mainActivityViewModel.responseDataForBooking?.let {
                            appointmentViewModel.bookAppointment(
                                BookingResource(
                                    it.id,
                                    it.resourceType,
                                    if (mainActivityViewModel.addedNotesState.value == ADD_NOTES_DEFAULT_TEXT) "" else mainActivityViewModel.addedNotesState.value,
                                    ServiceType(
                                        Coding(
                                            code = "code",
                                            system = null,
                                            display = mainActivityViewModel.selectedAppointmentData.reason
                                        )
                                    ),
                                    it.participant,
                                    it.slot,
                                    it.start,
                                    it.end
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = viewPort),
                    colors = ButtonDefaults.buttonColors(backgroundColor = customCyan)
                ) {
                    Text(
                        text = "SCHEDULE APPOINTMENT",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.W700,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = viewPort)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun AddNotesBottomSheetUi(
    mainActivityViewModel: MainActivityViewModel,
    onDoneClicked: () -> Unit
) {
    val addNotesText = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = addNotesText.value,
            onValueChange = {
                addNotesText.value = it
            },
            label = { Text("Add the notes here") },
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.Black)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                mainActivityViewModel.addedNotesState.value = addNotesText.value
                mainActivityViewModel.selectedAppointmentData.addedNotes = addNotesText.value
                onDoneClicked()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Done")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ReviewDataCardUi(category: String, data: String, @DrawableRes icon: Int, modifier: Modifier = Modifier) {

    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .align(CenterVertically),

                tint = customCyan
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.W700,
                    color = Color.Black
                )
                Text(
                    text = data,
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray.copy(0.8f),
                    maxLines = 2
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(Modifier.height(1.dp), color = lightGrey)
    }
}
