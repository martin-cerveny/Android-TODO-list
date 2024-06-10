package cz.cvut.fit.cervem27.tasks.features.task.presentation.listTasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDismissContainer(
    item: T,
    onDelete: (T) -> Unit,
    painter: Painter,
    painterTint: Color,
    backgroundColor: Color,
    padding: PaddingValues,
    content: @Composable (T) -> Unit
) {
    var isDismissed by remember { mutableStateOf(false) }


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
            delay(300)
            onDelete(item)
        }
    }


    SwipeToDismiss(
        state = state,
        background = {
            DeleteBackground(
                swipeDismissState = state,
                painter = painter,
                backgroundColor = backgroundColor,
                painterTint = painterTint
            )
        },
        modifier = Modifier
            .padding(padding)

        ,
        dismissContent = { content(item) },
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    painter: Painter,
    painterTint: Color,
    backgroundColor: Color,
    swipeDismissState: DismissState,
) {
    if(swipeDismissState.dismissDirection == DismissDirection.EndToStart ||
        swipeDismissState.dismissDirection == DismissDirection.StartToEnd) {



        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor, shape = RoundedCornerShape(12.dp))
            ,
            contentAlignment = Alignment.Center
        ) {

            Icon(
                painter = painter,
                contentDescription = null,
                tint = painterTint,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}
