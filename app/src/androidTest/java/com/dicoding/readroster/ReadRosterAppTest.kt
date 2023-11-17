package com.dicoding.readroster

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.dicoding.readroster.model.FakeBookDataSource
import com.dicoding.readroster.ui.navigation.Screen
import com.dicoding.readroster.ui.theme.ReadRosterTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ReadRosterAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController
    @Before
    fun setUp() {
        composeTestRule.setContent {
            ReadRosterTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                ReadRosterApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    //ketika mengklik pada salah satu item buku, maka akan berpindah ke halaman detail buku
    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("BookList").performScrollToIndex(9)
        composeTestRule.onNodeWithText(FakeBookDataSource.dummyBooks[9].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailBook.route)
        composeTestRule.onNodeWithText(FakeBookDataSource.dummyBooks[9].title).assertIsDisplayed()
    }

    //memastikan bottom navigation berjalan dengan baik
    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_cart).performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    //memastikan back button pada halaman detail berjalan dengan baik
    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithTag("BookList").performScrollToIndex(4)
        composeTestRule.onNodeWithText(FakeBookDataSource.dummyBooks[4].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailBook.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    //melakukan checkout lalu menghapus item dari cart
    @Test
    fun navHost_checkout_rightBackStack() {
        composeTestRule.onNodeWithText(FakeBookDataSource.dummyBooks[4].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailBook.route)
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.minus_symbol).performClick()
        composeTestRule.onNodeWithTag("CartEmpty").assertIsDisplayed()
    }

    //memastikan halaman profile berjalan dengan baik dan menampilkan data yang benar
    @Test
    fun navigate_to_profile() {
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.name_profile).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.email_profile).assertIsDisplayed()
    }

    // melakukan pencarian dengan keyword yang benar, lalu cek apakah data yang dicari ada di list
    @Test
    fun validSearch() {
        val validSearchQuery = "how"
        composeTestRule.onNodeWithStringId(R.string.search_book).performTextInput(validSearchQuery)
        composeTestRule.onNodeWithText(validSearchQuery).assertIsDisplayed()
    }

    // melakukan pencarian dengan keyword yang salah, lalu cek apakah data yang dicari tidak ada di list
    @Test
    fun invalidSearch() {
        val invalidSearchQuery = "kk"
        composeTestRule.onNodeWithStringId(R.string.search_book).performTextInput(invalidSearchQuery)
        composeTestRule.onNodeWithTag("EmptyHome").assertIsDisplayed()
    }
}