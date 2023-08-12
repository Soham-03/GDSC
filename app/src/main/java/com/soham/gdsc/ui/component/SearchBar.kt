package com.soham.gdsc.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soham.gdsc.ui.theme.Yellow
import com.soham.gdsc.ui.theme.textColorGrey

@Composable
fun SearchBar(){
    Box(modifier = Modifier.padding(horizontal = 16.dp))
    {
        var text by remember{ mutableStateOf(TextFieldValue(""))}
        Box(modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .height(50.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(30.dp))
            .background(Yellow, RoundedCornerShape(30.dp))
        )
        TextField(
            value = text,
            onValueChange = { it->
                text = it
            },
            leadingIcon = {
                Image(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search Image",
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            },
            placeholder = {
                Text(text = "Search", color = Color.Black)
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = textColorGrey,
                backgroundColor = Color.White,
                leadingIconColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(30.dp))
        )
    }
}

@Preview
@Composable
fun SearchBarPreview(){
    SearchBar()
}