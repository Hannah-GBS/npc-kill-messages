package ejedev.toxicnpcs;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ToxicNpcsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ToxicNpcsPlugin.class);
		RuneLite.main(args);
	}
}