package com.example.boltfax_compose.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.boltfax_compose.R
import com.example.boltfax_compose.ui.BoltFax_ComposeTheme
import com.example.boltfax_compose.ui.ButtonCustom
import com.example.boltfax_compose.ui.ColorPrimary
import com.example.boltfax_compose.ui.StyledTextInput


@Composable
fun ScreenLogin(navController: NavHostController) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        val painter = painterResource(id = R.drawable.logo)

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            painter = painter,
            contentDescription = "logo"
        )

//        RoundedContainer(cornerRadius = 14.dp) {
        Spacer(Modifier.height(16.dp))
        Text(
            "Sign In",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(16.dp))

        StyledTextInput(
            keyboardType = KeyboardType.Phone,
            placeholder = "Enter mobile number",
            onValueChange = {}
        )

        Spacer(Modifier.height(16.dp))

        StyledTextInput(
            keyboardType = KeyboardType.Password,
            placeholder = "Enter password",
            onValueChange = {},

            )

        Spacer(Modifier.height(16.dp))


        Text(
            text = "Forget Password?",
            color = Color.Red,
            modifier = Modifier

                .align(Alignment.End)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )
            Text(" OR ", style = MaterialTheme.typography.bodySmall)
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )

        }

        StyledTextInput(
            keyboardType = KeyboardType.Number,
            placeholder = "OTP",
            onValueChange = {},

            )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Get OTP",
            color = ColorPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier

                .align(Alignment.End)

        )

        ButtonCustom(text = "Login") {

        }


    }


}


@Preview(
    name = "Phone",
    device = "id:pixel_3_xl",
    showSystemUi = true
)
@Preview(
    name = "Tablet",
    device = "id:pixel_tablet",
    showSystemUi = true,
)
//@Preview(
//    name = "TV",
//    device = "id:tv_1080p",
//    showSystemUi = true
//)
@Preview(showBackground = true)
@Composable
fun ScreenLoginPreview() {

    val fakeNavController = rememberNavController()
    BoltFax_ComposeTheme {
        ScreenLogin(fakeNavController)
    }

}