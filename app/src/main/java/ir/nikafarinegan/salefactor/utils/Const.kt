package ir.nikafarinegan.salefactor.utils

object Const {
    const val KEY_PREFERENCE_NAME = "supervisors"
    const val KEY_ACCESS_TOKEN = "access_token"
    const val KEY_USER_NAME = "username"
    const val KEY_PASSWORD = "password"
    const val KEY_USER_THUMBNAIL = "user_thumbnail"
    const val KEY_USER_FAMILY = "family"
    const val KEY_USER_POSITION = "position"
    const val KEY_FINANCIAL_YEAR_ID = "financial_year_id"
    const val KEY_START_DATE = "start_date"
    const val KEY_END_DATE = "end_date"
    const val KEY_PERSONNEL_ID = "personnelId"
    const val KEY_USER_ID = "userId"
    const val KEY_LOG_OUT = "logout"
    const val KEY_OFFLINE_MODE = "offline_mode"
    const val KEY_REMEMBER_ME = "remember_me"


    const val KEY_SSID = "ss_id"
    const val KEY_FORM_ID = "form_id"
    const val KEY_SUB_SYSTEM_NAME = "sub_system_name"


    const val KEY_ITEM_NAME = "item_name"
    const val KEY_GOODS_ID = "goods_id"
    const val KEY_WDD_ID = "wdd_id"
    const val KEY_GOODS = "goods"

    const val KEY_IMEI = "imei"
    const val KEY_OS_VERSION = "os_version"
    const val KEY_DEVICE_MODEL = "device_model"
    const val KEY_APP_VERSION = "app_version"


    object SubSystems {
        const val KEY_BASE_INFORMATION = 98901
        const val KEY_OPERATION = 98915
        const val KEY_DASHBOARD = 98929
        const val KEY_WAREHOUSE_HANDLING = 98943
        const val KEY_REPORTS = 98957
    }

    object OperationSubSystem {
        const val KEY_GOODS_RECEIPT = 9891507
        const val KEY_GOODS_RETURNED = 9891509
        const val KEY_WAREHOUSE_REFERRED = 9891508
    }

    object BaseInformation{
        const val KEY_CUSTOMERS = 9890114 // طرف حساب ها
    }

    object ControlValidation {
        object Document {
            const val KEY_ADD = 0
            const val KEY_EDIT = 2
        }

        object DocumentDetail {
            const val KEY_ADD = 1
            const val KEY_EDIT = 2
        }
        object SubDocumentDetail {
            const val KEY_ADD = 1
            const val KEY_EDIT = 2
        }
    }

    const val SSID = 989
    const val APP_NAME = "WarehouseManagement"
    const val PACKAGE_NAME = "ir.nikafarinegan.warehousemanagement"
    const val PAGE_SIZE = 50
}