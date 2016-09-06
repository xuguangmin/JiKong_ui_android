package phlx.jikong.video;

import android.view.Surface;
/*import org.videolan.libvlc.IVideoPlayer;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.LibVlcException;*/

/*public class Player implements IVideoPlayer
{
	public interface Callback 
	{
		void setSurfaceSize(int width, int height);
	}
	
//	private LibVLC          mLibVLC = null; 
	private String          m_mediaPath = null;     
	private Callback        m_callback = null;
	
    @Override
	public void setSurfaceSize(int width, int height, int visible_width,
			int visible_height, int sar_num, int sar_den) 
	{
		if (width * height == 0)  
            return;  
		
		m_callback.setSurfaceSize(width, height);	
	}
    
    public int GetTime()
    {
    	return (int) mLibVLC.getTime();
    }
    public int GetLength()
    {
    	return (int) mLibVLC.getLength();
    }
    public void detachSurface()
    {
    	if(mLibVLC != null) mLibVLC.detachSurface();
    }
    public void attachSurface(Surface surface)
    {
    	mLibVLC.attachSurface(surface, this);    	
    }
    public void PlayPause()
	{
    	if (mLibVLC.isPlaying())
    		mLibVLC.pause();
        else
        	mLibVLC.play();
	}
    public void Stop()
	{
    	if (mLibVLC.isPlaying())
    		mLibVLC.stop();
	}
	
	public boolean Play(String mediaPath)
	{
		
		m_mediaPath = LibVLC.PathToURI(mediaPath);	
		System.out.printf("file = %s\n", m_mediaPath);
		
		 this.mLibVLC.playMRL(m_mediaPath);
		return true;
	}
	
	public boolean Initial(Callback callback)
	{
		try {  
            //LibVLC.useIOMX(getApplicationContext());  
            mLibVLC = VLCInstance.getLibVlcInstance(); 
            mLibVLC.eventVideoPlayerActivityCreated(true);
        } 
        catch (LibVlcException e) 
        {  
            e.printStackTrace();  
            return false;
        }  
		
		m_callback = callback;
		return true;
	}
}
*/