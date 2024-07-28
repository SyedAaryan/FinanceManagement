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
                if(title == "Add Reason"){
                viewmodel.addReason(onSuccess = {}, onFailure = {})
                viewmodel.reason = ""
                onDismissRequest()
                }else if(title == "Delete Reason"){
                    viewmodel.deleteReason(onSuccess = {}, onFailure = {})
                    onDismissRequest()
                }else{
                    viewmodel.updateReason(onSuccess = {}, onFailure = {})
                    onDismissRequest()
                }
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

                    DropDownForReason(viewModel = viewmodel)

                }
            }else{
                Column {

                    Text(text = "Select a reason to update")

                    DropDownForReason(viewModel = viewmodel)

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