package com.karegraf.androidarchitecturestarterkit.presentation.common.components

import android.content.ContentValues
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.core.content.PermissionChecker
import com.karegraf.androidarchitecturestarterkit.presentation.common.model.DialogStyle
import com.karegraf.androidarchitecturestarterkit.presentation.common.model.DialogType
import com.karegraf.androidarchitecturestarterkit.presentation.common.model.DialogUiState
import com.karegraf.androidarchitecturestarterkit.presentation.common.model.toStyle
import com.karegraf.androidarchitecturestarterkit.presentation.ui.theme.displayFontFamily

@Composable
fun ResultDialog(
    state: DialogUiState,
) {
    val style = state.type.toStyle()

    AlertDialog(
        icon = {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    imageVector = style.icon!!,
                    modifier = Modifier
                        .size(46.dp)
                        .align(Alignment.Center),
                    contentDescription = state.message,
                    tint = style.iconTint,

                    )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = style.iconTint,
                    modifier = Modifier
                        .size(26.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        },
        title = {
            Text(text = state.title,
                fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
    )
        },
        text = {
            Text(
                text = state.message,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        onDismissRequest = {
            state.onDismiss()
        },
        confirmButton = {
            style.confirmButtonContainerColor?.let {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = it,
                        contentColor = style.confirmButtonTextColor
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 10.dp
                    ),
                    shape = MaterialTheme.shapes.large,
                    onClick = { state.onConfirm() }
                ) {
Text(

                    text = state.confirmLabel,
                    color = style.confirmButtonTextColor,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
)


                }
            }
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = style.cancelButtonTextColor,
                    containerColor = style.cancelButtonContainerColor,
                ),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 10.dp
                ),
                shape = MaterialTheme.shapes.large,
                onClick = {
                    state.onDismiss()
                }
            ) {
                Text(
                    text = state.dismissLabel,
                    color = style.cancelButtonTextColor,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
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
            title = "Evrak Yukleme",
            message = "Evrak yukleme islemi basarisiz oldu.",
        )
    )
}