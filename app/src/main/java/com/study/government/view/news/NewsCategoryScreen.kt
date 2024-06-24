package com.study.government.view.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.study.government.R
import com.study.government.model.news.NewsCategory
import com.study.government.model.news.NewsCategory.entries
import com.study.government.tools.Background

@Composable
fun NewsCategoryScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (NewsCategory) -> Unit
) {
   Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Spacer(Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.select_category),
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            fontWeight = SemiBold,
            fontSize = 20.sp,
            color = DarkGray
        )

        Spacer(Modifier.height(20.dp))

        LazyVerticalGrid(
            horizontalArrangement = spacedBy(10.dp),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            columns = Fixed(2)
        ) {
            items(entries) { category ->
                Card(
                    onClick = { onCategoryClick(category) },
                    modifier = Modifier.size(
                        LocalConfiguration.current
                            .screenWidthDp.div(2)
                            .minus(50).dp
                    ),
                    elevation = cardElevation(2.dp),
                    colors = cardColors(White)
                ) {
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(category.icon),
                            contentDescription = null,
                            modifier = Modifier.size(60.dp),
                            tint = category.color
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = stringResource(category.label),
                            modifier = Modifier.padding(6.dp),
                            textAlign = TextAlign.Center,
                            color = category.color,
                            fontWeight = Medium,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}