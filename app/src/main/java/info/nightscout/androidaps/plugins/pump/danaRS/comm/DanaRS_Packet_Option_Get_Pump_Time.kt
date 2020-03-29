package info.nightscout.androidaps.plugins.pump.danaRS.comm

import info.nightscout.androidaps.logging.AAPSLogger
import info.nightscout.androidaps.logging.LTag
import info.nightscout.androidaps.plugins.pump.danaR.DanaRPump
import info.nightscout.androidaps.plugins.pump.danaRS.encryption.BleEncryption
import info.nightscout.androidaps.utils.DateUtil
import org.joda.time.DateTime

class DanaRS_Packet_Option_Get_Pump_Time(
    private val aapsLogger: AAPSLogger,
    private val danaRPump: DanaRPump
) : DanaRS_Packet() {

    init {
        opCode = BleEncryption.DANAR_PACKET__OPCODE_OPTION__GET_PUMP_TIME
        aapsLogger.debug(LTag.PUMPCOMM, "Requesting pump time")
    }

    override fun handleMessage(data: ByteArray) {
        var dataIndex = DATA_START
        var dataSize = 1
        val year = byteArrayToInt(getBytes(data, dataIndex, dataSize))
        dataIndex += dataSize
        dataSize = 1
        val month = byteArrayToInt(getBytes(data, dataIndex, dataSize))
        dataIndex += dataSize
        dataSize = 1
        val day = byteArrayToInt(getBytes(data, dataIndex, dataSize))
        dataIndex += dataSize
        dataSize = 1
        val hour = byteArrayToInt(getBytes(data, dataIndex, dataSize))
        dataIndex += dataSize
        dataSize = 1
        val min = byteArrayToInt(getBytes(data, dataIndex, dataSize))
        dataIndex += dataSize
        dataSize = 1
        val sec = byteArrayToInt(getBytes(data, dataIndex, dataSize))
        val time = DateTime(2000 + year, month, day, hour, min, sec)
        danaRPump.pumpTime = time.millis
        aapsLogger.debug(LTag.PUMPCOMM, "Pump time " + DateUtil.dateAndTimeString(time.millis))
    }

    override fun handleMessageNotReceived() {
        danaRPump.pumpTime = 0
    }

    override fun getFriendlyName(): String {
        return "OPTION__GET_PUMP_TIME"
    }
}