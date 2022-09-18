package dev.elrol.serverutilities.commands.rank.parent;

import dev.elrol.serverutilities.Main;
import dev.elrol.serverutilities.commands.ModSuggestions;
import dev.elrol.serverutilities.data.Rank;
import dev.elrol.serverutilities.init.Ranks;
import dev.elrol.serverutilities.libs.text.Errs;
import dev.elrol.serverutilities.libs.text.Msgs;
import dev.elrol.serverutilities.libs.text.TextUtils;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class RankParentRemove {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return (Commands.literal("remove")
                .executes(RankParentRemove::execute))
                .then(Commands.argument("rank", StringArgumentType.string())
                        .suggests(ModSuggestions::suggestRanks)
                        .executes(RankParentRemove::execute)
                        .then(Commands.argument("parent", StringArgumentType.string())
                            .suggests(ModSuggestions::suggestRanks)
                            .executes(RankParentRemove::execute)));
    }

    private static int execute(CommandContext<CommandSourceStack> c) {
        String name = StringArgumentType.getString(c, "rank");
        String parent = StringArgumentType.getString(c, "parent");
        if (name.isEmpty()) {
            Main.textUtils.err(c, Errs.no_rank_name());
            return 0;
        }
        if (!Ranks.rankMap.containsKey(name)) {
            Main.textUtils.err(c, Errs.rank_doesnt_exist(name));
            return 0;
        }
        if(!Ranks.rankMap.containsKey(parent)){
            Main.textUtils.err(c, Errs.rank_doesnt_exist(parent));
            return 0;
        }
        Rank rank = Ranks.rankMap.get(name);
        rank.removeParent(parent);
        Main.textUtils.msg(c, Msgs.parentRankRemove.get(name, parent));
        return 1;
    }
}

