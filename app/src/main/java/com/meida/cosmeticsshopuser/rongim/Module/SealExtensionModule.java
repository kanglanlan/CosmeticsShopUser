package com.meida.cosmeticsshopuser.rongim.Module;


import com.meida.cosmeticsshopuser.rongim.IPPlugin.RedPlugin;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.DefaultLocationPlugin;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imlib.model.Conversation;

public class SealExtensionModule extends DefaultExtensionModule {

    @Override
    public List getPluginModules(Conversation.ConversationType conversationType) {
        List pluginModuleList = new ArrayList<>();
        IPluginModule image = new ImagePlugin();
        IPluginModule location = new DefaultLocationPlugin();
        IPluginModule redPlugin = new RedPlugin();


//       IPluginModule audio = new AudioPlugin();
//       IPluginModule video = new VideoPlugin();
//       IPluginModule file = new FilePlugin();

        if (conversationType.equals(Conversation.ConversationType.GROUP) ||
                conversationType.equals(Conversation.ConversationType.DISCUSSION) ||
                conversationType.equals(Conversation.ConversationType.PRIVATE)) {
            pluginModuleList.add(image);
            pluginModuleList.add(location);
            pluginModuleList.add(redPlugin);
//           pluginModuleList.add(audio);
//           pluginModuleList.add(video);
//           pluginModuleList.add(file);
        } else {
            pluginModuleList.add(image);
        }

        return pluginModuleList;
    }

    @Override
    public List getEmoticonTabs() {
        return super.getEmoticonTabs();
    }


}