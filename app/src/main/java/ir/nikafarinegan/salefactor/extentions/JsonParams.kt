package ir.nikafarinegan.salefactor.extentions

/**
 * Created by Ali_Kazemi on 23/10/2021.
 */
fun subSystemJson(
    subSystemId: Int
): String {
    return "{\\\"NIK_SsParentIDs_fk\\\":\\\"${subSystemId}\\\"}"
}

fun recoverPasswordJson(
    userId: String
): String {
    return "{\\\"TBL_UserName\\\":\\\"$userId\\\"}"
}

fun checkConfirmationCodeJson(
    confirmationCode: Int
): String {
    return "{\\\"TBL_UsdLastConfirmationCode\\\":\\\"$confirmationCode\\\"}"
}

fun changePasswordJson(
    newPassword: String,
    confirmPassword: String,
    oldPassword: String
): String {
    return "{\\\"TBL_UserPassword\\\":\\\"${newPassword}\\\",\\\"TBL_UserPasswordConfirm\\\":\\\"${confirmPassword}\\\",\\\"TBL_UserPasswordOld\\\":\\\"${oldPassword}\\\"}"
}

fun documentTypeListJson(formParentId: Long): String {
    return "{\\\"TBL_FormParentIDs_fk\\\":\\\"$formParentId\\\"}"
}

fun warehouseDocumentListJson(
    warehouseId: Long,
    formId: Long,
    search: String
): String {
    return "{\\\"WHS_WarehouseIDs_fk\\\":\\\"$warehouseId\\\",\\\"TBL_FormIDs_fk\\\":\\\"$formId\\\",\\\"ExpressionSearch\\\":\\\"${search}\\\"}"
}

fun warehouseDocumentDetailJson(wdId: Long): String {
    return "{\\\"WHS_WdIDs_fk\\\":\\\"$wdId\\\"}"
}

fun warehouseDocumentSubDetailJson(wddId: Long): String {
    return "{\\\"WHS_WddIDs_fk\\\":\\\"$wddId\\\"}"
}

fun searchGoodsListJson(
    warehouseId: Long,
    search: String,
    startDate: String
): String {
    return "{\\\"ExpressionSearch\\\":\\\"${search}\\\",\\\"WHS_WarehouseIDs_fk\\\":\\\"$warehouseId\\\",\\\"StartRange\\\":\\\"${startDate}\\\"}"
}

fun projectListJson(
    woId: Long,
    search: String
): String{
    return "{\\\"WOS_WoIDs_fk\\\":\\\"$woId\\\",\\\"ExpressionSearch\\\":\\\"${search}\\\"}"
}

fun searchJson(search: String): String{
    return "{\\\"ExpressionSearch\\\":\\\"${search}\\\"}"
}

fun contractJson(
    customerId: Long,
    search: String
): String{
    return "{\\\"ExpressionSearch\\\":\\\"${search}\\\",\\\"TBL_CustomerIDs_fk\\\":\\\"$customerId\\\"}"
}

fun getDocumentNumberJson(
formId: Long,
warehouseId: Long
): String{
    return "{\\\"WHS_WarehouseIDs_fk\\\":\\\"$warehouseId\\\",\\\"TBL_FormIDs_fk\\\":\\\"$formId\\\"}"
}

fun reserveWarehouseDocumentJson(
    formId: Long,
    warehouseId: Long,
    search: String
): String{
    return "{\\\"WHS_WarehouseIDs_fk\\\":\\\"$warehouseId\\\",\\\"TBL_FormIDs_fk\\\":\\\"$formId\\\",\\\"ExpressionSearch\\\":\\\"${search}\\\"}"
}

fun customerAccountJson(
    customerId: Long,
    search: String
): String{
    return "{\\\"ExpressionSearch\\\":\\\"${search}\\\",\\\"TBL_CustomerIDs_fk\\\":\\\"$customerId\\\"}"
}

fun documentControlValidationJson(
warehouseId: Long,
formId: Long,
documentStatus: Int
): String{
    return "{\\\"WHS_WarehouseIDs_fk\\\":\\\"$warehouseId\\\",\\\"TBL_FormIDs_fk\\\":\\\"$formId\\\",\\\"WHS_WdConfirmByUser\\\":\\\"0\\\",\\\"WarehouseDocumentStatus\\\":\\\"$documentStatus\\\"}"
}

fun documentDetailControlValidationJson(
wdId: Long,
documentDetailStatus: Int
): String{
    return "{\\\"WHS_WdIDs_fk\\\":\\\"$wdId\\\",\\\"WarehouseDocumentDetailStatus\\\":\\\"$documentDetailStatus\\\"}"
}

fun documentSubDetailControlValidationJson(
    wddId: Long,
    documentSubDetailStatus: Int
): String{
    return "{\\\"WHS_WddIDs_fk\\\":\\\"$wddId\\\",\\\"WarehouseDocumentSDetailStatus\\\":\\\"$documentSubDetailStatus\\\"}"
}