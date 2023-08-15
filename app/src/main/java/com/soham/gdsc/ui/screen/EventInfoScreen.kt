package com.soham.gdsc.ui.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.soham.gdsc.MainActivity
import com.soham.gdsc.R
import com.soham.gdsc.model.Event
import com.soham.gdsc.ui.component.EventSingleRow
import com.soham.gdsc.ui.theme.cardBackgroundGreen
import com.soham.gdsc.ui.theme.textColorGrey

@Composable
fun EventInfoScreen(
    eventName: String,
    eventDate: String,
    eventTime: String
){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState(), true)
    )
    {
        Box() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "back btn",
                    modifier = Modifier
                        .size(64.dp)
                        .clickable {
                            val intent = Intent(context, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            context.startActivity(intent)
                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_question),
                    contentDescription = "question ic",
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            Text(
                text = "The Event",
                color = textColorGrey,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .padding(horizontal =  20.dp)
        )
        {
            EventSingleRow(
                backgroundColor = cardBackgroundGreen,
                eventImage = "",
                eventName = eventName,
                eventDate = eventDate,
                eventTime = eventTime,
                onEventClick = {
                }
            )
        }
        Text(
            text = "About the event",
            color = textColorGrey,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
        )
        Text(
            text = "‼ BitNBuild: Hack the future at our event! ‼\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBB\n" +
                "\n" +
                "GDSC UMIT and GDSC CRCE have joined hands to organize first OFFLINE hackathon for the year 2023!!\n" +
                "\n" +
                "Yes, you read it right! It's a 24 hour offline hackathon and guess what, Food is on us!\uD83C\uDF5D\n" +
                "\n" +
                "Pre-hackathon:\n" +
                "\n" +
                "(21st January 2023 - 26th January 2023)\n" +
                "\n" +
                "● 21st January at 6:00pm - Problem Statement Release - Idea submission for round 1 begins\n" +
                "\n" +
                "● 24th January at 11:59pm - Idea submission ends\n" +
                "\n" +
                "● 26th January at 7:00pm - Announcement of the participants qualified for round 2, which will be held OFFLINE at Fr. Conceicao Rodrigues College of Engineering, Bandra.\n" +
                "\n" +
                "\uD83D\uDDD3Date of the offline hackathon: 28th January - 29th January 2023\n" +
                "\n" +
                "\uD83D\uDD60Time: 2:00pm (28th January) to 6:00pm (29th January)\n" +
                "\n" +
                "\uD83D\uDCCDVenue: Fr Conceicao Rodrigues College of Engineering, Bandstand, Bandra(West), Mumbai\n" +
                "\n" +
                "\uD83E\uDDFEDomains in BitNBuild :\n" +
                "\n" +
                "\uD83D\uDD38App/Web Development\n" +
                "\n" +
                "\uD83D\uDD38Open Innovation\n" +
                "\n" +
                "\uD83D\uDD38Blockchain\n" +
                "\n" +
                "\uD83D\uDD38AI/ML\n" +
                "\n" +
                "PRIZE POOL OF INR 70,000+\uD83D\uDCB0\n" +
                "\n" +
                "Certificate of participation will be given to all teams with valid submissions\uD83D\uDD16\n" +
                "\n" +
                "♦️ Participants: 2-4 members per team\n" +
                "\n" +
                "♦️ Full Agenda for 28th and 29th January is up on our website : bitnbuild.netlify.app\n" +
                "\n" +
                "♦️ Registration link: bit-n-build.devfolio.co\n" +
                "\n" +
                "‼ Register Now!!‼\n" +
                "\n" +
                "Last date of registration: 24th January 2023, 11:59pm",
            color =  textColorGrey,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .height(200.dp)
                .verticalScroll(rememberScrollState(), true)
        )
        Text(
            text = "When and Where",
            color = textColorGrey,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
        )
        //when and where
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Image(
                painter = painterResource(id = R.drawable.outline_schedule_black_24dp),
                contentDescription = "When Icon",
                modifier = Modifier
                    .padding(16.dp)
                    .size(42.dp)
            )
            Text(
                text = "28-july",
                color = textColorGrey,
                fontSize = 16.sp,
            )
            Image(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "Where Icon",
                modifier = Modifier
                    .padding(16.dp)
                    .size(42.dp)
            )
            Text(
                text = "Fr Agnel Ashram\n" +
                        "Bandstand Promenade\n" +
                        "W, Maharashtra, 400050",
                color = textColorGrey,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(end = 16.dp)
            )

        }
    }
}

@Preview
@Composable
fun EventInfoPreview(){
    EventInfoScreen(eventName = "", eventDate = "", eventTime = "")
}