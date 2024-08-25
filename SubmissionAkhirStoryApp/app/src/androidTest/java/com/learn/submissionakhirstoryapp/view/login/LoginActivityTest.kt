package com.learn.submissionakhirstoryapp.view.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.learn.submissionakhirstoryapp.R
import com.learn.submissionakhirstoryapp.view.main.MainActivity
import com.learn.submissionakhirstoryapp.view.utils.EspressoIdlingResource
import com.learn.submissionakhirstoryapp.view.welcome.WelcomeActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }
    @After
    fun tearDown() {
        Intents.release()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loginSuccess() {
        onView(withId(R.id.emailEditText)).perform(typeText("layout@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText)).perform(typeText("1sampai8"), closeSoftKeyboard())
        onView(withId(R.id.loginButton)).perform(click())

        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))

        onView(withText(R.string.alert_login_success)).inRoot(isDialog()).check(matches(isDisplayed()))
        onView(withId(android.R.id.button1)).perform(click())

        intended(hasComponent(MainActivity::class.java.name))

        onView(withId(R.id.rv_stories)).check(matches(isDisplayed()))

        // Logout
        onView(withId(R.id.menu_logout)).perform(click())
        intended(hasComponent(WelcomeActivity::class.java.name))
        onView(withText(R.string.title_welcome_page)).check(matches(isDisplayed()))
    }

    @Test
    fun loginFailure() {
        onView(withId(R.id.emailEditText)).perform(typeText("invalid_email"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText)).perform(typeText("invalid_password"), closeSoftKeyboard())
        onView(withId(R.id.loginButton)).perform(click())

        // Verifikasi apakah pesan kesalahan ditampilkan
        onView(withText(R.string.wrong_email_and_password)).inRoot(RootMatchers.isDialog()).check(matches(isDisplayed()))
    }
}
