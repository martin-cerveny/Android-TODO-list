package cz.cvut.fit.cervem27.tasks.features.notification.presentation

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
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import cz.cvut.fit.cervem27.tasks.R


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Permission(){

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        val permissionState = rememberPermissionState(permission)

        if (! permissionState.status.isGranted) {
            if (permissionState.status.shouldShowRationale) {
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text(text = stringResource(R.string.notifications)) },
                    text = { Text(text = stringResource(R.string.notification_permission_rationale)) },
                    confirmButton = {
                        Button(onClick = { permissionState.launchPermissionRequest() }) {
                            Text(text = stringResource(id = R.string.ok))
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