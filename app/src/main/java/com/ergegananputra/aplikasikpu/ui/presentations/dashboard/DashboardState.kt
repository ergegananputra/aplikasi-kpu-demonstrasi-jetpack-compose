package com.ergegananputra.aplikasikpu.ui.presentations.dashboard

data class DashboardState(
    val appVersion : String = "tidak diketahui",
    val isDialogOpen : Boolean = false,

    val errorMessage : String? = null
)
