package com.junigates.j_cc_picker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import coil.compose.rememberAsyncImagePainter
import com.junigates_test.jcountrycodepicker.ui.theme.Charcoal
import com.junigates_test.jcountrycodepicker.ui.theme.CountryCodeColor
import com.junigates_test.jcountrycodepicker.ui.theme.Grey_Light
import com.junigates_test.jcountrycodepicker.ui.theme.TextColor4
import com.servicemarket.app.utils.jCountryData.JCountryData
import com.servicemarket.app.utils.jCountryData.JCountryListType

@Composable
fun JCountryCodePicker(
    defaultCountryCode: String = "ARE",
    onDefaultFlagChange: (JCountryListType.JCountry?) -> Unit = {},
    onItsValueChange: (value: String) -> Unit = {},
    mobileNumber: String = ""
) {

    var defaultFlag by remember { mutableStateOf(JCountryData.getSingleCountry(defaultCountryCode)) }
    var name by remember { mutableStateOf(mobileNumber) }
    var showCountryList by remember { mutableStateOf(false) }

    //selected flag or base flag
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(0.5.dp, Color.Gray, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp)
            .clickable {

                showCountryList = true

            },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row (
            modifier = Modifier.clickable {
                showCountryList = true
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape),
                painter = rememberAsyncImagePainter(defaultFlag?.flagUrl),
                contentDescription = defaultFlag?.countryName,
                contentScale = ContentScale.FillBounds,
            )

            Text(
                text = defaultFlag?.countryCode ?: "",
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = CountryCodeColor
                )
            )

            Box(modifier = Modifier
                .padding(start = 12.dp, end = 5.dp)
                .height(20.dp)
                .width(1.dp)
                .background(color = Grey_Light))
        }

        TextField(
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone, contentDescription = "",
                    tint = TextColor4
                )
            },
            modifier = Modifier.weight(1f),
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            ),

            placeholder = { Text("501234567", color = TextColor4) },

            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Charcoal,
                unfocusedTextColor = Charcoal,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            value = name,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    name = it
                    onItsValueChange(it)
                }
            }
        )
    }

    if(showCountryList){
        CodePickerCountryList(
            selectedJCountryListType = defaultFlag,
            showSheet = showCountryList,
            onCountrySelected = {
                defaultFlag = it
                onDefaultFlagChange(defaultFlag)
            },
        ) {
            showCountryList = false
        }
    }

}

@Composable
fun showIT() {
    JCountryCodePicker()
}