package ir.nikafarinegan.salefactor.settings

import ir.awlrhm.modules.view.changesDialog.ReleaseChangeModel
import ir.nikafarinegan.salefactor.R

val listChanges: List<ReleaseChangeModel>
    get() = listOf(
        ReleaseChangeModel("1.1.0", R.array.release_1_0_0)
    )

val isSecure: Boolean
    get() = false






