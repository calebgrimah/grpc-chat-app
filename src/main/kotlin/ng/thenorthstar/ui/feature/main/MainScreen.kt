package ng.thenorthstar.ui.feature.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ng.thenorthstar.ui.value.R


@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    val welcomeText by viewModel.welcomeText.collectAsState()
    val messages = viewModel.messageFrom
    var fromText by remember { mutableStateOf(TextFieldValue("")) }
    var message by remember { mutableStateOf(TextFieldValue("")) }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth().align(Alignment.Start).weight(8f)) {
                items(
                    items = messages,
                    itemContent = {
                        Text(it, color = Color.Red)
                    }
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(32.dp)
            ) {
                TextField(
                    value = fromText,
                    onValueChange = { newText ->
                        fromText = newText
                    },
                    placeholder = { Text(text = "Enter your name!") },
                    maxLines = 1
                )
                Spacer(Modifier.padding(32.dp, 1.dp))
                TextField(
                    value = message,
                    onValueChange = { newText ->
                        message = newText
                    },
                    placeholder = { Text(text = "Enter your message!") },
                    maxLines = 1
                )
                Spacer(Modifier.padding(32.dp, 1.dp))
                Button(
                    onClick = {
                        viewModel.sendNewMessage(fromText.text, message.text)
                        message = TextFieldValue("")
                    }
                ) {
                    Text(text = R.string.SENDD_MESSAGE)
                }
            }
        }
    }
}