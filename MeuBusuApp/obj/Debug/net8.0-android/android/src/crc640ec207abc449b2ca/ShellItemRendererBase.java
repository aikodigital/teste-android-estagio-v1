package crc640ec207abc449b2ca;


public abstract class ShellItemRendererBase
	extends androidx.fragment.app.Fragment
	implements
		mono.android.IGCUserPeer
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onDestroy:()V:GetOnDestroyHandler\n" +
			"";
		mono.android.Runtime.register ("Microsoft.Maui.Controls.Platform.Compatibility.ShellItemRendererBase, Microsoft.Maui.Controls", ShellItemRendererBase.class, __md_methods);
	}


	public ShellItemRendererBase ()
	{
		super ();
		if (getClass () == ShellItemRendererBase.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Controls.Platform.Compatibility.ShellItemRendererBase, Microsoft.Maui.Controls", "", this, new java.lang.Object[] {  });
		}
	}


	public ShellItemRendererBase (int p0)
	{
		super (p0);
		if (getClass () == ShellItemRendererBase.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Controls.Platform.Compatibility.ShellItemRendererBase, Microsoft.Maui.Controls", "System.Int32, System.Private.CoreLib", this, new java.lang.Object[] { p0 });
		}
	}


	public void onDestroy ()
	{
		n_onDestroy ();
	}

	private native void n_onDestroy ();

	private java.util.ArrayList refList;
	public void monodroidAddReference (java.lang.Object obj)
	{
		if (refList == null)
			refList = new java.util.ArrayList ();
		refList.add (obj);
	}

	public void monodroidClearReferences ()
	{
		if (refList != null)
			refList.clear ();
	}
}
