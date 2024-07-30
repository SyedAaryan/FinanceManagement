import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financemanagement.view.components.DropDownForReason
import com.example.financemanagement.view.components.InputTextField
import com.example.financemanagement.viewmodel.SettingsViewModel

@Composable
fun SettingsViewDialog(
    title: String,
    onDismissRequest: () -> Unit,
    viewmodel: SettingsViewModel = viewModel()
) {

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = {
                when (title) {
                    "Add Reason" -> {
                        viewmodel.addReason(onSuccess = {}, onFailure = {})
                        viewmodel.reason = ""
                    }
                    "Delete Reason" -> {
                        viewmodel.deleteReason(onSuccess = {}, onFailure = {})
                    }
                    "Update Reason" -> {
                        viewmodel.updateReason(onSuccess = {}, onFailure = {})
                    }
                }
                onDismissRequest()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        },
        text = {
            if(title == "Add Reason"){
                Column {
                    InputTextField(
                        label = "Add Reason",
                        value = viewmodel.reason,
                        onValueChanged = {
                            viewmodel.reason = it
                        }
                    )
                }
            }else if (title == "Delete Reason"){
                Column {

                    Text(text = "Select a reason to delete")

                    DropDownForReason(reasonsMap = viewmodel.reasonsMap, selectedReasonKey = viewmodel.selectedReasonKey)

                }
            }else{
                Column {

                    Text(text = "Select a reason to update")

                    DropDownForReason(reasonsMap = viewmodel.reasonsMap, selectedReasonKey = viewmodel.selectedReasonKey)

                    InputTextField(
                        label = "Update Reason",
                        value = viewmodel.newReason,
                        onValueChanged = {
                            viewmodel.newReason = it
                        }
                    )

                }
            }
        }
    )
}