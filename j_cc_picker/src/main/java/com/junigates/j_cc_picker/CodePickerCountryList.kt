package com.junigates.j_cc_picker


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.junigates.j_cc_picker.ui.mySpacer
import com.junigates.j_cc_picker.ui.myWidthSpacer
import com.junigates_test.jcountrycodepicker.ui.theme.Black_12
import com.junigates_test.jcountrycodepicker.ui.theme.Charcoal
import com.junigates_test.jcountrycodepicker.ui.theme.Grey_Light30
import com.servicemarket.app.utils.jCountryData.JCountryData
import com.servicemarket.app.utils.jCountryData.JCountryListType
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodePickerCountryList(
    showSheet: Boolean,
    selectedJCountryListType: JCountryListType.JCountry? = null,
    onCountrySelected: (JCountryListType.JCountry?) -> Unit,
    onDismiss: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { newState ->
            newState != SheetValue.Hidden //  Stop bottom sheet from hiding on outside press
        },
        skipPartiallyExpanded = true // Forces the sheet to fully expand
    )

    var searchJCountry by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val columnState = rememberLazyListState()
    val countries = remember { JCountryData.getAllCountriesList() }

//    val sheetState = rememberBottomSheetState()

    if (showSheet) {

        ModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            sheetState = sheetState,
            properties = ModalBottomSheetProperties(
                shouldDismissOnBackPress = false
            ),
            dragHandle = null,
            containerColor = Color.White,
            onDismissRequest = {
                onDismiss()
            }) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {

                mySpacer(h = 5)
                Row(Modifier.fillMaxWidth()) {
                    myWidthSpacer(5)
                    TextButton(onClick = {

                        coroutineScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            onDismiss()
                        }

                    }) {
                        Text(
                            text = "Close",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                mySpacer(h = 10)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    OutlinedTextField(
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                tint = Color.Gray,
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        trailingIcon = {
                            if (searchJCountry.isNotEmpty()) {
                                IconButton(onClick = { searchJCountry = "" }) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                                }
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Grey_Light30,
                            unfocusedContainerColor = Grey_Light30,
                            focusedBorderColor = Grey_Light30,
                            unfocusedBorderColor = Grey_Light30
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        value = searchJCountry, onValueChange = {
                            searchJCountry = it
                        },
                        placeholder = {
                            Text(
                                text = "Search",
                                color = Color.Gray,
                                fontSize = 15.sp
                            )
                        }
                    )
                }

//default country
                if (selectedJCountryListType != null) {
                    mySpacer(h = 20)
                    Box(modifier = Modifier.fillMaxWidth()) {
                        countryItem(
                            modifier = Modifier.align(Alignment.CenterStart),
                            jCountry = selectedJCountryListType,
                            onCountrySelected = onCountrySelected
                        )
                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check, contentDescription = "default country check"
                            )
                            myWidthSpacer(w = 20)
                        }
                    }
                }

                mySpacer(h = 20)

                LazyColumn(
                    state = columnState,
                    modifier = Modifier.fillMaxSize()
                ) {

                    items(
                        items = countries.filter {
                            (it is JCountryListType.JCountry
                                    &&
                                    (
                                            it.alpha2.contains(searchJCountry, true) ||
                                                    it.alpha3.contains(searchJCountry, true) ||
                                                    it.countryCode.contains(searchJCountry, true) ||
                                                    it.countryName.contains(searchJCountry, true)
                                            )
                                    ) ||
                                    (it is JCountryListType.JCountryFirstAlphabet && it.alphabet.contains(
                                        searchJCountry,
                                        true
                                    ))
                        }
                    ) {
                        if (it is JCountryListType.JCountry) {
                            countryItem(it) { selected ->
                                coroutineScope.launch {
                                    sheetState.hide()
                                }.invokeOnCompletion {
                                    onCountrySelected(selected)
                                    onDismiss()
                                }
                            }
                        } else {
                            firstAlphabet(it as JCountryListType.JCountryFirstAlphabet)
                        }
                    }

                }

            }
        }

    }

}

@Composable
fun firstAlphabet(firstAlphabet: JCountryListType.JCountryFirstAlphabet) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Black_12)
            .padding(vertical = 10.dp, horizontal = 20.dp)
    ) {
        Text(
            text = firstAlphabet.alphabet,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            )
        )
    }
}

@Composable
fun countryItem(
    jCountry: JCountryListType.JCountry,
    modifier: Modifier = Modifier,
    onCountrySelected: (JCountryListType.JCountry?) -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
            .clickable {
                onCountrySelected(jCountry)
            }
            .padding(vertical = 10.dp, horizontal = 15.dp)
            .then(modifier)
        ,

        verticalAlignment = Alignment.CenterVertically

    ) {
        Image(
            modifier = Modifier
                .height(30.dp)
                .width(40.dp)
                .border(0.1.dp, Color.Gray, shape = RoundedCornerShape(5.dp)),
            painter = rememberAsyncImagePainter(jCountry.flagUrl), contentDescription = "",
            contentScale = ContentScale.FillBounds,
        )

        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = jCountry.countryName,
            style = TextStyle(
                fontSize = 18.sp,
                color = Charcoal
            ),
            maxLines = 1
        )
    }
}