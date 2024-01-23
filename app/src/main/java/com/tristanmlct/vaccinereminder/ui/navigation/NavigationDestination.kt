package com.tristanmlct.vaccinereminder.ui.navigation


/**
 * Interface for the navigation destination
 */
interface NavigationDestination {
    /**
     * Unique name for path
     */
    val route: String

    /**
     * String resource id that contains the title to be displayed
     */
    val titleRes: Int
}