/*package phlx.jikong.video;

import android.content.Context;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.LibVlcException;
import phlx.jikong.JKApplication;

public class VLCInstance 
{
	public static LibVLC getLibVlcInstance() throws LibVlcException 
	{
        LibVLC instance = LibVLC.getExistingInstance();
        if (instance == null) 
        {
            instance = LibVLC.getInstance();
            final Context context = JKApplication.getAppContext();
           
            VLCInstance.updateLibVlcSettings();
            instance.init(context);
        }
        return instance;
    }
	
	public static void updateLibVlcSettings() 
	{
        LibVLC instance = LibVLC.getExistingInstance();
        if (instance == null)
            return;

        //instance.setSubtitlesEncoding(pref.getString("subtitle_text_encoding", ""));
        //instance.setTimeStretching(pref.getBoolean("enable_time_stretching_audio", false));
        //instance.setFrameSkip(pref.getBoolean("enable_frame_skip", false));
        //instance.setChroma(pref.getString("chroma_format", ""));
        //instance.setVerboseMode(pref.getBoolean("enable_verbose_mode", true));

       // if (pref.getBoolean("equalizer_enabled", false))
            //instance.setEqualizer(Preferences.getFloatArray(pref, "equalizer_values"));

        
       
        int networkCaching = pref.getInt("network_caching_value", 0);
        if(networkCaching > 60000)
            networkCaching = 60000;
        else if(networkCaching < 0)
            networkCaching = 0;
        instance.setAout(-1);
        instance.setVout(-1);
        //instance.setDeblocking(deblocking);
        //instance.setNetworkCaching(networkCaching);
        instance.setHardwareAcceleration(LibVLC.HW_ACCELERATION_DISABLED);
    }


}
*/