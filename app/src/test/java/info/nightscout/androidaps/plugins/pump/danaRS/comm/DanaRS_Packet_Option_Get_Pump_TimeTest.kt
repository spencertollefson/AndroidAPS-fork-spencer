package info.nightscout.androidaps.plugins.pump.danaRS.comm

import org.joda.time.DateTime
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
class DanaRS_Packet_Option_Get_Pump_TimeTest : DanaRSTestBase() {

    @Test fun runTest() {
        val packet = DanaRS_Packet_Option_Get_Pump_Time(aapsLogger, danaRPump)
        val array = createArray(8, 0.toByte()) // 6 + 2
        putByteToArray(array, 0, 19) // year 2019
        putByteToArray(array, 1, 2) // month february
        putByteToArray(array, 2, 4) // day 4
        putByteToArray(array, 3, 20) // hour 20
        putByteToArray(array, 4, 11) // min 11
        putByteToArray(array, 5, 35) // second 35

        packet.handleMessage(array)
        Assert.assertEquals(DateTime(2019, 2, 4, 20, 11, 35).millis, danaRPump.pumpTime)
        Assert.assertEquals("OPTION__GET_PUMP_TIME", packet.friendlyName)
    }
}