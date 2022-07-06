package com.exploids.hashmapz.ui.onboarding

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.exploids.hashmapz.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

val onboardPages = listOf(
    Page(
        "Welcome to HashmapsZ",
        "HashmapZ is an interactive learning app. It helps you understand the fundamentals of hash maps by visualizing the operations add, delete and search.",
        R.drawable.hashmapz_icon
    ),
    Page(
        "The Home Screen",
        "The home screen is where the hash map is displayed. The array index is represented on the left. If a key and value are written into the array, it is shown as a node. In this example the key is \"Tangerine\" and the hash code is 1933294285. On the other hand, when a slot is empty, the index is displayed without a node next to it.",
        R.drawable.home
    ),
    Page(
        "Updating, Deleting and Searching",
        "With the plus button you can add an entry to the hash map. The key will then be hashed and is used to determine the array index to put your entry in. If the slot is occupied then the algorithm searches for the next available slot to put your entry in. To delete an entry use the minus button and to search for an entry use the magnifying glass next to it.",
        R.drawable.action
    ),
    Page(
        "Creating a new map",
        "The Create Button lets your create a new hash map with a different Probing Mode and Load Factor. But be careful the existing hash map will be deleted!",
        R.drawable.renew
    )
)

@Composable
fun PageView(page: Page) {
    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(page.image),
            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = page.title,
            fontSize = 28.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = page.description,
            textAlign = TextAlign.Center, fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnboardingView(onEnd: () -> Unit) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            count = onboardPages.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->

            PageView(page = onboardPages[page])
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            activeColor = colorResource(R.color.purple_500)
        )

        Crossfade(
            modifier = Modifier.fillMaxWidth(),
            targetState = pagerState.currentPage < onboardPages.lastIndex
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (it) {
                        OutlinedButton(onClick = onEnd) {
                            Text(text = "Skip tutorial")
                        }
                        Button(
                            onClick = {
                                if (!pagerState.isScrollInProgress) {
                                    scope.launch {
                                        pagerState.animateScrollToPage(
                                            minOf(
                                                pagerState.currentPage + 1,
                                                onboardPages.lastIndex
                                            )
                                        )
                                    }
                                }
                            }
                        ) {
                            Text(text = "Next")
                        }
                    } else {
                        Button(onClick = onEnd) {
                            Text(text = "Get started")
                        }
                    }
                }
            }
        }
    }
}