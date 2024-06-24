package com.study.government.view.servants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.study.government.GovernmentApp.Companion.curScreen
import com.study.government.R
import com.study.government.model.Servant
import com.study.government.model.servantsPresets
import com.study.government.navigation.Destination.SERVANTS
import com.study.government.navigation.Destination.SERVANTS_INFO
import com.study.government.navigation.DestinationArg.SERVANT_INFO_ARG
import com.study.government.navigation.NavArgument
import com.study.government.navigation.Navigation.navigateTo
import com.study.government.tools.Background
import com.study.government.tools.PrimaryColor

@Composable
fun ServantsScreen(navHostController: NavHostController) {
    LaunchedEffect(Unit) { curScreen = SERVANTS }

    Scaffold { padding ->
        LazyColumn(
            verticalArrangement = spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(horizontal = 16.dp)
                .padding(padding)
        ) {
            item { Spacer(Modifier) }

            item {
                Text(
                    text = stringResource(R.string.servants_list),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = PrimaryColor,
                    fontWeight = SemiBold,
                    fontSize = 24.sp
                )
            }

            item { Spacer(Modifier) }

            items(servantsPresets) { servant ->
                ServantItem(servant) {
                    navHostController.navigateTo(
                        arg = NavArgument(
                            value = servant.id.toString(),
                            argument = SERVANT_INFO_ARG
                        ),
                        dest = SERVANTS_INFO
                    )
                }
            }

            item { Spacer(Modifier) }
        }
    }
}

@Composable
private fun ServantItem(
    servant: Servant,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.height(Max),
        elevation = cardElevation(2.dp),
        colors = cardColors(White),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            ServantAvatar(servant, 80.dp)

            Spacer(Modifier.width(8.dp))

            Column {
                Text(
                    text = servant.name,
                    fontWeight = Medium,
                    fontSize = 18.sp,
                    color = DarkGray
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = servant.post,
                    fontWeight = Normal,
                    fontSize = 14.sp,
                    color = Gray
                )
            }
        }
    }
}