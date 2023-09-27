package com.soham.gdsc.ui.screen

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.soham.gdsc.Global
import com.soham.gdsc.MainActivity
import com.soham.gdsc.R
import com.soham.gdsc.firebaseAuth.SignedInState
import com.soham.gdsc.firebaseDB.FirestoreViewModel
import com.soham.gdsc.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun  SignInScreen(
    state: SignedInState,
    onSignInClick:() -> Unit
){
    var name by remember{ mutableStateOf(TextFieldValue("")) }
    var phoneNo by remember{ mutableStateOf(TextFieldValue("")) }
    var collegeName by remember{ mutableStateOf(TextFieldValue("")) }
    LaunchedEffect(key1 = state.signInError){
        state.signInError.let {
            println("Failed")
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        val context = LocalContext.current
        val activity = context.findActivity()
        val user = FirebaseAuth.getInstance().currentUser
        //background elements
        Column(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            Image(
                painter = painterResource(id = R.drawable.ic_gdsc_logo),
                contentDescription = "GDSC Logo",
                modifier = Modifier
                    .padding(16.dp)
                    .height(80.dp)
                    .align(Alignment.Start)
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            {
                var height by remember{ mutableStateOf(0.dp) }
                val localDensity = LocalDensity.current
                Box(modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(height)
                    .border(2.dp, Color.Black, RoundedCornerShape(30.dp))
                    .background(cardBackgroundGreen, RoundedCornerShape(30.dp))
                )
                Card(
                    backgroundColor = Color.White,
                    border = BorderStroke(2.dp, Color.Black),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            height = with(localDensity) { coordinates.size.height.toDp() }
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                    {
                        Text(
                            text = "Hello, Developers",
                            color = textColorGrey,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Complete Your Profile & Sign Up <3",
                            color = textColorGrey,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        OutlinedTextField(
                            value = name,
                            onValueChange = {
                                name = it
                            },
                            shape = RoundedCornerShape(30.dp),
                            label = {
                                Text(
                                    text = "What is your name?",
                                    color = textColorGrey,
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = textColorGrey,
                                focusedBorderColor = LightBlue,
                                unfocusedBorderColor = textColorGrey
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            ),
                            modifier = Modifier
                                .padding(top = 12.dp)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                        )
                        OutlinedTextField(
                            value = phoneNo,
                            onValueChange = {
                                if (it.text.length <= 10) phoneNo = it
                            },
                            shape = RoundedCornerShape(30.dp),
                            label = {
                                Text(
                                    text = "Phone Number?",
                                    color = textColorGrey,
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = textColorGrey,
                                focusedBorderColor = LightBlue,
                                unfocusedBorderColor = textColorGrey
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone
                            ),
                            modifier = Modifier
                                .padding(top = 12.dp)
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                        )
                        val coffeeDrinks = arrayOf("FE COMPS A", "FE COMPS B", "FE AIDS", "FE ECS", "SE COMPS A", "SE COMPS B", "SE AIDS", "SE ECS", "TE COMPS A", "TE COMPS B", "TE AIDS", "TE ECS", "BE COMPS A", "BE COMPS B", "BE AIDS", "BE ECS")
                        var expanded by remember { mutableStateOf(false) }
                        Box(
                            modifier = Modifier
                                .padding(top = 12.dp)
                                .fillMaxWidth()
                        ){
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = {
                                    expanded = !expanded
                                },
                                modifier = Modifier
                                    .fillMaxWidth()

                            ){
                                OutlinedTextField(
                                    value = collegeName,
                                    onValueChange = {
                                    },
                                    shape = RoundedCornerShape(30.dp),
                                    label = {
                                        Text(
                                            text = "Your Class",
                                            color = textColorGrey,
                                        )
                                    },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        textColor = textColorGrey,
                                        focusedBorderColor = LightBlue,
                                        unfocusedBorderColor = textColorGrey,
                                        trailingIconColor = Color.Black
                                    ),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text
                                    ),
                                    enabled = false,
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    modifier = Modifier
                                        .background(Color.White),
                                ) {
                                    coffeeDrinks.forEach { item ->
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = { Text(text = item) },
                                            onClick = {
                                                collegeName = TextFieldValue(item)
                                                expanded = false
                                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                            },
                                            colors = MenuDefaults.itemColors(
                                                textColor = textColorGrey
                                            )
                                            )
                                    }
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(top = 12.dp)
                        )
                        {
                            Box(modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                                .height(60.dp)
                                .border(2.dp, Color.Black, RoundedCornerShape(30.dp))
                                .background(Yellow, RoundedCornerShape(30.dp))
                            )
                            Box(contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .border(2.dp, Color.Black, RoundedCornerShape(30.dp))
                                    .background(Color.White, RoundedCornerShape(30.dp))
                                    .clickable {
                                        if (!TextUtils.isEmpty(name.text) && !TextUtils.isEmpty(
                                                phoneNo.text
                                            ) && !TextUtils.isEmpty(collegeName.text)
                                        ) {
                                            Global.collegeName = collegeName.text
                                            Global.phoneNumber = phoneNo.text
                                            onSignInClick.invoke()
                                        } else {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Please fill the above fields",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        }
//                                        val intent = Intent(context, MainActivity::class.java)
//                                        context.startActivity(intent)
//                                        activity.finish()
                                    }
                            )
                            {
                                Text(
                                    text = "Continue with Google",
                                    color = textColorGrey,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                }
            }
            Text(
                text = "Already have an account?",
                fontSize = 14.sp,
                color = LightRed,
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Button(
                shape = RoundedCornerShape(30.dp),
                border = BorderStroke(2.dp, Color.Black),
                onClick = {
                    onSignInClick.invoke()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Yellow,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Log In with Google", fontSize = 18.sp)
            }
            Image(
                painter = painterResource(id = R.drawable.bg_bottom),
                contentDescription = "bg Bottom",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }
    }
}

//function to find activity from local context
//used above where context is assigned
internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}


@Preview
@Composable
fun SignInPreview(){
//    SignInScreen(SignedInState(false, null),{},)
}