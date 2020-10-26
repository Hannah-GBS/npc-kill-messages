package ejedev.toxicnpcs;

import com.google.inject.Inject;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.events.ActorDeath;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.InteractingChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.List;

@PluginDescriptor(
        name = "Toxic NPCs",
        description = "NPCs will now flame you if you are weak enough to die to them.",
        tags = {"death", "npc", "npcs", "toxic", "flame", "pk"},
        loadWhenOutdated = true,
        enabledByDefault = false
)
public class ToxicNPCSPlugin extends Plugin {

    @Inject
    private Client client;

    private Actor opponent;

    private boolean textSet;

    private int tickCount = 0;

    List<String> farewells = Arrays.asList("L000000000000L sit #frontline", "sit nerd", "gf lol", "whale ty", "f3 loc??", "Racial Slur", "lmfao #abos", "go back to LMS", "free l0l", "?", "sit", "sit idiot", "eat?");

    @Subscribe
    public void onInteractingChanged(InteractingChanged event)
    {
        if (event.getSource() != client.getLocalPlayer())
        {
            return;
        }
        if (event.getTarget() instanceof NPC) {
            opponent = event.getTarget();
        }
    }

    @Subscribe
    public void onActorDeath(ActorDeath actorDeath)
    {
        Actor actor = actorDeath.getActor();
        if (actor instanceof Player)
        {
            Player player = (Player) actor;
            if (player == client.getLocalPlayer())
            {
                opponent.setOverheadText(farewells.get(ThreadLocalRandom.current().nextInt(0, farewells.size() + 1)));
                textSet = true;
            }
        }
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (textSet) {
            if (tickCount == 6) {
                opponent.setOverheadText(null);
                tickCount = 0;
                textSet = false;
            }
            else if (tickCount < 6) {
                tickCount += 1;
            }
        }
    }
}
