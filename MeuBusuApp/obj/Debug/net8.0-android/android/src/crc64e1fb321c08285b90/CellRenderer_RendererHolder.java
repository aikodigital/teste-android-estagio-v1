package crc64e1fb321c08285b90;


public class CellRenderer_RendererHolder
	extends java.lang.Object
	implements
		mono.android.IGCUserPeer
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"";
		mono.android.Runtime.register ("Microsoft.Maui.Controls.Handlers.Compatibility.CellRenderer+RendererHolder, Microsoft.Maui.Controls", CellRenderer_RendererHolder.class, __md_methods);
	}


	public CellRenderer_RendererHolder ()
	{
		super ();
		if (getClass () == CellRenderer_RendererHolder.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Controls.Handlers.Compatibility.CellRenderer+RendererHolder, Microsoft.Maui.Controls", "", this, new java.lang.Object[] {  });
		}
	}

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
