package com.dicoding.readroster.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.readroster.R
import com.dicoding.readroster.ui.theme.ReadRosterTheme


//@Composable
//fun BookItem(
//    image: Int,
//    title: String,
//    author: String,
//    price: Int,
//    modifier: Modifier = Modifier
//) {
//    Row(
//        modifier = modifier
//            .clip(RoundedCornerShape(8.dp))
//    ) {
//        Image(
//            painter = painterResource(image),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .size(140.dp)
//        )
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//                .weight(1.0f)
//        ) {
//            Text(
//                text = author,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis,
//                style = MaterialTheme.typography.titleSmall.copy(
//                    fontWeight = FontWeight.Medium
//                )
//            )
//            Text(
//                text = title,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis,
//                style = MaterialTheme.typography.titleMedium.copy(
//                    fontWeight = FontWeight.ExtraBold
//                )
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = stringResource(R.string.price, price.toString()),
//                style = MaterialTheme.typography.titleLarge.copy(
//                    fontWeight = FontWeight.ExtraBold
//                ),
//            )
//        }
//    }
//}

@Composable
fun BookItem(
    image: Int,
    title: String,
    author: String,
    price: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(170.dp)
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(170.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.secondary
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = author,
                maxLines = 1,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = stringResource(R.string.price, price),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BookItemPreview() {
    ReadRosterTheme {
        BookItem(R.drawable.book_1, "How to Get Rich with Online Business", "Richar Wijaya",15)
    }
}
