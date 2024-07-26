import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financemanagement.view.components.InputTextField
import com.example.financemanagement.viewmodel.SettingsViewModel

@Composable
fun SettingsViewDialog(
    onDismissRequest: () -> Unit,
    viewmodel: SettingsViewModel = viewModel()
) {

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = {
                viewmodel.addReason(onSuccess = {}, onFailure = {})
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
            Column {
                InputTextField(
                    label = "Add Reason",
                    value = viewmodel.reason,
                    onValueChanged = {
                        viewmodel.reason = it
                    }
                )
            }
        }
    )
}