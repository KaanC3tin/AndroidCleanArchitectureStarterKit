package com.karegraf.androidarchitecturestarterkit.presentation.common.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.karegraf.androidarchitecturestarterkit.presentation.common.model.DialogType
import com.karegraf.androidarchitecturestarterkit.presentation.common.model.DialogUiState

@Composable
fun ResultDialog(
    state: DialogUiState,
    onDismiss: () -> Unit
) {
    val icon = when (state.type) {
        DialogType.SUCCESS -> Icons.Default.CheckCircle
        DialogType.ERROR -> Icons.Default.Close
    }
    val title = when (state.type) {
        DialogType.SUCCESS -> "Başarılı"
        DialogType.ERROR -> "Hata olustu"
    }
    AlertDialog(
        icon = {
            Icon(imageVector = icon, contentDescription = title)
        },
        title = {
            Text(text = title)
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    (onDismiss())
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Dismiss")
            }
        }
    )

}

