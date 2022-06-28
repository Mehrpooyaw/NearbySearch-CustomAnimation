package com.example.nearbysearch.presentation.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.nearbysearch.domain.enums.BaseSections
import com.example.nearbysearch.presentation.ui.theme.RedSunsetGradient

@Composable
fun BottomNavBar(
    modifier : Modifier = Modifier,
    tabs : Array<BaseSections>,
    navController: NavController,
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute =  navBackStackEntry?.destination?.route
    val sections = remember {BaseSections.values()}
    val routes = remember {sections.map { it.route }}
    if (currentRoute in routes) {
        val currentSection = sections.first {
            it.route == currentRoute
        }
        Box(
            modifier = modifier.fillMaxWidth()
        ){
            Spacer(
                modifier
                    .padding(horizontal = 1.dp)
                    .clip(RoundedCornerShape(topEndPercent = 25, topStartPercent = 25))
                    .fillMaxWidth()
                    .height(63.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colors.background.copy(alpha = 0.01f),
                                MaterialTheme.colors.background.copy(alpha = 0.2f),
                                MaterialTheme.colors.background.copy(alpha = 0.5f),
                                MaterialTheme.colors.background.copy(alpha = 0.8f),
                                MaterialTheme.colors.background.copy(alpha = 0.9f),
                                MaterialTheme.colors.background

                            )
                        )
                    )
                    .blur(50.dp)
            )

        }
        Card(
            modifier = modifier
                .padding(horizontal = 10.dp,)
                .fillMaxWidth()
                .padding(bottom = 5.dp)
                .height(60.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 20.dp
        ) {
            Box(
                modifier.background(
                    Brush.linearGradient(
                        tileMode = TileMode.Mirror,

                        colors = RedSunsetGradient
                    )
                )

            ) {
                Spacer(modifier = modifier
                    .fillMaxSize()
                    .background(
                        if (MaterialTheme.colors.isLight)
                            Color.Black.copy(0.1f)
                        else
                            Color.Black.copy(0.1f)
                    ))
                BottomNavigation(

                    modifier = modifier
                        .fillMaxSize()

                        .shadow(elevation = 0.dp, clip = true, shape = RoundedCornerShape(10.dp))
                    ,
                    elevation = 0.dp,
                    backgroundColor =
                    if (MaterialTheme.colors.isLight)
                        Color.Transparent
                    else
                        Color.Transparent,



                ) {
                    tabs.forEach { section ->
                        val selected = section == currentSection
                        val tint by animateColorAsState(
                            if (selected) Color.White else Color.White.copy(alpha = 0.85f)
                        )
                        val brightness by animateColorAsState(
                            if (selected) Color.White else Color.Transparent
                        )
                        val scale by animateFloatAsState(targetValue =
                        if (selected) 1f else 0.8f,
                            animationSpec = spring(
                                dampingRatio = 0.5f,
                                stiffness = Spring.StiffnessMedium
                            )
                        )
                        val position by animateIntAsState(targetValue =
                        if (selected) 5 else 11,
                            animationSpec = spring(
                                dampingRatio = 0.3f,
                                stiffness = Spring.StiffnessMedium
                            )
                        )

                        BottomNavigationItem(
                            selected = selected,
                            onClick = {
                                if (section.route != currentRoute) {
                                    if (section.route != currentRoute) {
                                        navController.navigate(section.route) {
                                            launchSingleTop = true
                                            restoreState = true
                                            popUpTo(findStartDestination(navController.graph).id) {
                                                saveState = true
                                            }
                                        }
                                    }
                                }
                            },
                            icon = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = modifier.padding(top = position.dp)
                                ) {
                                    if (section.icon != null) {
                                        Icon(
                                            imageVector = section.icon,
                                            contentDescription = "",
                                            tint = tint,
                                            modifier = modifier
                                                .graphicsLayer {
                                                    scaleX = scale
                                                    scaleY = scale
                                                    alpha = scale
                                                }
                                                .size(28.dp)
                                        )

                                    } else {
                                        section.resource?.let {
                                            Icon(
                                                painter = painterResource(id = it),
                                                contentDescription = "",
                                                tint = tint,
                                                modifier = modifier
                                                    .graphicsLayer {
                                                        scaleX = scale
                                                        scaleY = scale
                                                        alpha = scale
                                                    }
                                                    .size(28.dp)
                                            )
                                        }

                                    }
                                    Spacer(modifier = modifier.height(5.dp))
                                    Text(
                                        text =section.str.uppercase(),
                                        color = brightness,
                                        style = MaterialTheme.typography.caption,
                                        fontSize = 9.sp
                                    )
                                }
                            },

                            )
                    }
                }
            }
        }
    }
}
private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)


private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}
