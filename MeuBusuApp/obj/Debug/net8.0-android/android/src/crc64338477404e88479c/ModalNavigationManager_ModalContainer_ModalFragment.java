package crc64338477404e88479c;


public class ModalNavigationManager_ModalContainer_ModalFragment
	extends androidx.fragment.app.Fragment
	implements
		mono.android.IGCUserPeer
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onCreateView:(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;:GetOnCreateView_Landroid_view_LayoutInflater_Landroid_view_ViewGroup_Landroid_os_Bundle_Handler\n" +
			"";
		mono.android.Runtime.register ("Microsoft.Maui.Controls.Platform.ModalNavigationManager+ModalContainer+ModalFragment, Microsoft.Maui.Controls", ModalNavigationManager_ModalContainer_ModalFragment.class, __md_methods);
	}


	public ModalNavigationManager_ModalContainer_ModalFragment ()
	{
		super ();
		if (getClass () == ModalNavigationManager_ModalContainer_ModalFragment.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Controls.Platform.ModalNavigationManager+ModalContainer+ModalFragment, Microsoft.Maui.Controls", "", this, new java.lang.Object[] {  });
		}
	}


	public ModalNavigationManager_ModalContainer_ModalFragment (int p0)
	{
		super (p0);
		if (getClass () == ModalNavigationManager_ModalContainer_ModalFragment.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Controls.Platform.ModalNavigationManager+ModalContainer+ModalFragment, Microsoft.Maui.Controls", "System.Int32, System.Private.CoreLib", this, new java.lang.Object[] { p0 });
		}
	}


	public android.view.View onCreateView (android.view.LayoutInflater p0, android.view.ViewGroup p1, android.os.Bundle p2)
	{
		return n_onCreateView (p0, p1, p2);
	}

	private native android.view.View n_onCreateView (android.view.LayoutInflater p0, android.view.ViewGroup p1, android.os.Bundle p2);

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
