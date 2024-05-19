package com.example.musicapp.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicapp.R

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(colorResource(R.color.color_00AE72)),
        shape = RoundedCornerShape(20.dp),
        content = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    )
}

@Composable
fun RegisterButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, Color.Black),
        contentPadding = PaddingValues(12.dp),
        content = {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    )
}

@Composable
fun LoginwithGoogleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    imageDescription: String,
    image: Int
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        content = {
            Image(
                painter = painterResource(image),
                contentDescription = imageDescription
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Text(
                text = text,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    )
}
