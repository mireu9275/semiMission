package kr.eme.semiMission.objects.items



import kr.eme.semiMission.objects.models.ItemReward as IR
import org.bukkit.Material as M
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlotGroup
import kr.eme.semiMission.main

object ItemTemplates {
    // material
    // HIDE_ATTRIBUTES applied by default in ItemReward

    val ironOre = IR(M.RED_DYE, 1, "§f철", "", 3)

    val aluminumIngot = IR(M.RED_DYE, 1, "§f알루미늄 주괴", "", 20)
    val ironIngot = IR(M.RED_DYE, 1, "§f철 주괴", "", 21)
    val copperIngot = IR(M.RED_DYE, 1, "§f구리 주괴", "", 22)
    val lithiumIngot = IR(M.RED_DYE, 1, "§f리튬 주괴", "", 23)
    val platinumIngot = IR(M.RED_DYE, 1, "§f백금 주괴", "", 25)
    val nickelIngot = IR(M.RED_DYE, 1, "§f니켈 주괴", "",26)
    val titaniumIngot = IR(M.RED_DYE, 1, "§f티타늄 주괴", "", 27)
    val ALCUIngot = IR(M.RED_DYE, 1, "§f합금 주괴","§fAl-Cu 합금 주괴", 28)
    val CUAUIngot = IR(M.RED_DYE, 1, "§f합금 주괴", "§fCu-Au 합금 주괴", 31)

    // module
    val furnaceModule = IR(M.IRON_HORSE_ARMOR, 1, "§f용광로 모듈", "", 7)

    // recipe
    val ironIngotRecipe = IR(M.SADDLE, 1, "§f철 주괴 레시피", "", 25)
    val ALCURecipe = IR(M.SADDLE,1,"§fAl-Cu 합금 레시피","§f알루미늄 + 구리",32)

    // other
    val mug = IR(M.BOWL, 1, "§f머그잔", "§fJava", 1)
    val nutrientCapsule = IR(M.ORANGE_DYE, 1, "§f영양 캡슐", "§f더 높은 등급의 작물이 나올 확률이 증가합니다.", 2)
    val growthCapsule = IR(M.ORANGE_DYE, 1, "§f성장 캡슐", "§f작물의 수확일을 줄여줍니다.",3)
    val weedkillerCapsule = IR(M.ORANGE_DYE, 1, "§f제초 캡슐", "§f잡초를 제거합니다.", 4)

    val knife = IR(M.WOODEN_SHOVEL,1,"§f나이프","",10)
    val coffee = IR(M.BOWL,1,"§f커피","",2)

    val transportationItem = IR(
        material = M.IRON_BOOTS,
        amount = 1,
        name = "§f이동수단",
        metaModifier = {
            val stepModifier = AttributeModifier(
                NamespacedKey(main, "step_height"),
                1.0,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.FEET
            )
            addAttributeModifier(Attribute.STEP_HEIGHT, stepModifier)
        }
    )
    val longSword = IR(M.WOODEN_SHOVEL, 1, "§f장도","",11)
    val drill = IR(M.WOODEN_SHOVEL, 1, "§f가벼운 채굴기", "", 2)
}
