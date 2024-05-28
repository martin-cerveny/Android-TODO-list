package cz.cvut.fit.cervem27.tasks.core

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import cz.cvut.fit.cervem27.tasks.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDismissContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    painter: Painter,
    backgroundColor: Color,
    padding: PaddingValues,
    content: @Composable (T) -> Unit
) {
    var isDismissed by remember {
        mutableStateOf(false)
    }
    val state = rememberDismissState(
        confirmValueChange = { value ->
            if (value == DismissValue.DismissedToStart || value == DismissValue.DismissedToEnd ) {
                isDismissed = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isDismissed) {
        if(isDismissed) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isDismissed,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                DeleteBackground(swipeDismissState = state, painter = painter, backgroundColor = backgroundColor)
            },
            modifier = Modifier.padding(padding),
            dismissContent = { content(item) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    painter: Painter,
    backgroundColor: Color,
    swipeDismissState: DismissState
) {
    if(swipeDismissState.dismissDirection == DismissDirection.EndToStart ||
        swipeDismissState.dismissDirection == DismissDirection.StartToEnd) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF7AA24C)),
            contentAlignment = if(swipeDismissState.dismissDirection == DismissDirection.StartToEnd)
                                    Alignment.CenterStart else Alignment.CenterEnd,

        ) {
            Icon(
                painter = painter,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxHeight()
            )
        }
    }
}
