package com.dicoding.readroster.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.readroster.R
import com.dicoding.readroster.di.Injection
import com.dicoding.readroster.ui.ViewModelFactory
import com.dicoding.readroster.ui.common.UiState
import com.dicoding.readroster.ui.components.OrderButton
import com.dicoding.readroster.ui.components.ProductCounter
import com.dicoding.readroster.ui.theme.ReadRosterTheme

@Composable
fun DetailScreen(
    rewardId: Long,
    viewModel: DetailRewardViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getRewardById(rewardId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.book.image,
                    data.book.title,
                    data.book.author,
                    data.book.description,
                    data.book.price,
                    data.count,
                    onAddToCart = { count ->
                        viewModel.addToCart(data.book, count)
                        navigateToCart()
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    author: String,
    description: String,
    basePrice: Int,
    count: Int,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    var totalPoint by rememberSaveable { mutableIntStateOf(0) }
    var orderCount by rememberSaveable { mutableIntStateOf(count) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Start,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Light
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = stringResource(R.string.price, basePrice),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Light
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                }
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .padding(16.dp)
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                )
                Text(
                    text = stringResource(R.string.detail_author, author),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = modifier
                        .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp)
                )
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(Color.LightGray))
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.select_quantity))
                ProductCounter(
                    1,
                    orderCount,
                    onProductIncreased = { orderCount++ },
                    onProductDecreased = { if (orderCount > 0) orderCount-- },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
            }
            totalPoint = basePrice * orderCount
            OrderButton(
                text = stringResource(R.string.add_to_cart, totalPoint),
                enabled = orderCount > 0,
                onClick = {
                    onAddToCart(orderCount)
                }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DetailContentPreview() {
//    ReadRosterTheme {
//        DetailContent(
//            R.drawable.book_1,
//            "Jaket Hoodie Dicoding",
//            "dadasdsad",
//            "A motivational book that provides tips and tricks on how to start, grow, and optimize an online business in the digital age.",
//            15,
//            1,
//            onBackClick = {},
//            onAddToCart = {}
//        )
//    }
//}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    ReadRosterTheme {
        DetailContent(
            R.drawable.book_1,
            "How to Get Rich with Online Business",
            "Rizki Herlambang",
            "A motivational book that provides tips and tricks on how to start, grow, and optimize an online business in the digital age.",
            15,
            1,
            onAddToCart = {}
        )
    }
}
