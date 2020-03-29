package info.nightscout.androidaps.plugins.pump.danaRS.comm

import info.nightscout.androidaps.plugins.pump.danaRS.encryption.BleEncryption
import info.nightscout.androidaps.logging.AAPSLogger
import info.nightscout.androidaps.logging.LTag
import info.nightscout.androidaps.plugins.pump.danaR.DanaRPump
import info.nightscout.androidaps.utils.DateUtil
import org.joda.time.DateTime
import java.util.*

class DanaRS_Packet_Bolus_Get_Step_Bolus_Information(
    private val aapsLogger: AAPSLogger,
    private val danaRPump: DanaRPump
) : DanaRS_Packet() {

    init {
        opCode = BleEncryption.DANAR_PACKET__OPCODE_BOLUS__GET_STEP_BOLUS_INFORMATION
        aapsLogger.debug(LTag.PUMPCOMM, "New message")
    }

    override fun handleMessage(data: ByteArray) {
        val error = intFromBuff(data, 0, 1)
        val bolusType = intFromBuff(data, 1, 1)
        danaRPump.initialBolusAmount = intFromBuff(data, 2, 2) / 100.0
        val hours = intFromBuff(data, 4, 1)
        val minutes = intFromBuff(data, 5, 1)
        danaRPump.lastBolusTime = DateTime.now().withHourOfDay(hours).withMinuteOfHour(minutes).millis
        danaRPump.lastBolusAmount = intFromBuff(data, 6, 2) / 100.0
        danaRPump.maxBolus = intFromBuff(data, 8, 2) / 100.0
        danaRPump.bolusStep = intFromBuff(data, 10, 1) / 100.0
        failed = error != 0
        aapsLogger.debug(LTag.PUMPCOMM, "Result: $error")
        aapsLogger.debug(LTag.PUMPCOMM, "BolusType: $bolusType")
        aapsLogger.debug(LTag.PUMPCOMM, "Initial bolus amount: " + danaRPump.initialBolusAmount + " U")
        aapsLogger.debug(LTag.PUMPCOMM, "Last bolus time: " + DateUtil.dateAndTimeString(danaRPump.lastBolusTime))
        aapsLogger.debug(LTag.PUMPCOMM, "Last bolus amount: " + danaRPump.lastBolusAmount)
        aapsLogger.debug(LTag.PUMPCOMM, "Max bolus: " + danaRPump.maxBolus + " U")
        aapsLogger.debug(LTag.PUMPCOMM, "Bolus step: " + danaRPump.bolusStep + " U")
    }

    override fun getFriendlyName(): String {
        return "BOLUS__GET_STEP_BOLUS_INFORMATION"
    }
}