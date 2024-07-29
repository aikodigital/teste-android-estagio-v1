package com.example.olhovivo.ui.theme

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.olhovivo.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewDisplayed() {
        onView(withId(R.id.recyclerViewLinhas)).check(matches(isDisplayed()))
    }

    @Test
    fun testButtonClick() {
        onView(withId(R.id.buttonBuscar)).perform(click())
        onView(withText("Buscar")).check(matches(isDisplayed()))
    }
}
