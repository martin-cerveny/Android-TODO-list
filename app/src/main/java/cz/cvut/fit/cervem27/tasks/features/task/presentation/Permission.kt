package cz.cvut.fit.cervem27.tasks.features.task.presentation

import android.os.Build
import androidx.compose.runtime.Composable

import android.Manifest
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Permission(
    rationale: String,
) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        val permissionState = rememberPermissionState(permission)

        if (! permissionState.status.isGranted) {
            if (permissionState.status.shouldShowRationale) {
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text(text = "title") },
                    text = { Text(rationale) },
                    confirmButton = {
                        Button(onClick = { permissionState.launchPermissionRequest() }) {
                            Text(text = "ok")
                        }
                    }
                )
            } else {
                // Either user got a permission request for the first time or declined two times or more
                LaunchedEffect(Unit) {
                    permissionState.launchPermissionRequest()
                }
            }
        }
    }
}