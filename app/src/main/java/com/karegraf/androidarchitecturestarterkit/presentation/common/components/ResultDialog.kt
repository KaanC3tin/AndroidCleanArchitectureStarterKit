package com.karegraf.androidarchitecturestarterkit.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.core.content.PermissionChecker
import com.karegraf.androidarchitecturestarterkit.presentation.common.model.DialogStyle
import com.karegraf.androidarchitecturestarterkit.presentation.common.model.DialogType
import com.karegraf.androidarchitecturestarterkit.presentation.common.model.DialogUiState
import com.karegraf.androidarchitecturestarterkit.presentation.common.model.toStyle

@Composable
fun ResultDialog(
    state: DialogUiState,
) {
    val style = state.type.toStyle()

    AlertDialog(
        icon = {
            Icon(
                imageVector = style.icon!!,
                modifier = Modifier.size(46.dp),
                contentDescription = state.message,
                tint = style.iconTint
            )
        },
        title = {
            Text(text = state.title)
        },
        text = {
            Text(
                text = state.message,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        onDismissRequest = {
            state.onDismiss()
        },
        confirmButton = {
            TextButton(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .background(
                        style.confirmButtonContainerColor,
                        MaterialTheme.shapes.large
                    ),
                onClick = {
                    state.onConfirm()
                }
            ) {
                Text(
                    state.confirmLabel,
                    color = (style.confirmButtonTextColor)
                )
            }
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = style.cancelButtonTextColor,
                    containerColor = style.cancerButtonContainerColor,
                    ),
                onClick = {
                    state.onDismiss()
                }
            ) {
                Text(
                    state.dismissLabel,
                )
            }
        }
    )

}

@Composable
@Preview(showBackground = true, device = "spec:width=411dp,height=891dp", showSystemUi = false)
fun ResultDialogPreview() {
    ResultDialog(
        state = DialogUiState(
            type = DialogType.SUCCESS,
            title = "Evrak Yukleme",
            message = "Evrak yukleme islemi basarili.",
        )
    )
}


@Composable
@Preview(
    device = "spec:width=411dp,height=891dp",
    wallpaper = Wallpapers.NONE
)
fun ResultErrorDialogPreview() {
    ResultDialog(
        state = DialogUiState(
            type = DialogType.ERROR,
            title =  "Evrak Yukleme",
            message = "Evrak yukleme islemi basarisiz oldu.",
        )
    )
}