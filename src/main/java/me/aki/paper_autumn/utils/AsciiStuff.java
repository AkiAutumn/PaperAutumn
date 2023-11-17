package me.aki.paper_autumn.utils;

import org.bukkit.Bukkit;

import java.util.Random;
import java.util.logging.Level;

public class AsciiStuff {

    public static void randomAsciiArtToConsole(){
        Random r = new Random();
        int low = 3;
        int high = 16;
        int result = r.nextInt(high-low) + low;
        switch (result) {
            case 3 -> LogFrogs();
            case 4 -> LogBisasam();
            case 5 -> LogCat();
            case 6 -> LogCatBox();
            case 7 -> LogCatPainting();
            case 8 -> LogDuck();
            case 9 -> LogDucks();
            case 10 -> LogFatCat();
            case 11 -> LogFlamingo();
            case 12 -> LogLittleBird();
            case 13 -> LogLoveBirds();
            case 14 -> LogPacman();
            case 15 -> LogSingingBirds();
            case 16 -> LogUnicorn();
        }
    }

    public static void LogFrogs(){
        String string = """

                              _         _
                  __   ___.--'_`.     .'_`--.___   __
                 ( _`.'. -   'o` )   ( 'o`   - .`.'_ )
                 _\\.'_'      _.-'     `-._      `_`./_
                ( \\`. )    //\\`         '/\\\\    ( .'/ )
                 \\_`-'`---'\\\\__,       ,__//`---'`-'_/
                  \\`        `-\\         /-'        '/
                   `                               '  \s""";

        Bukkit.getLogger().info(string);
    }

    public static void LogDucks(){
        String string = """

                  _      _      _
                >(.)__ <(.)__ =(.)__
                 (___/  (___/  (___/ \s""";

        Bukkit.getLogger().info(string);
    }

    public static void LogFlamingo(){
        String string = """

                         (\\/)
                          \\/
                  (\\/)   .-.  .-.
                   \\/   ((`-)(-`))
                         \\\\    //   (\\/)
                          \\\\  //     \\/
                   .=""\"=._))((_.=""\"=.
                  /  .,   .'  '.   ,.  \\
                 /__(,_.-'      '-._,)__\\
                `    /|             |\\   `
                    /_|__         __|_\\
                      | `))     ((` |
                      |             |
                jgs  -"==         =="-""";

        Bukkit.getLogger().info(string);
    }

    public static void LogDuck(){
        String string = """

                        ,----,
                   ___.`      `,
                   `===  D     :
                     `'.      .'
                        )    (                   ,
                       /      \\_________________/|
                      /                          |
                     |                           ;
                     |               _____       /
                     |      \\       ______7    ,'
                     |       \\    ______7     /
                      \\       `-,____7      ,'   jgs
                ^~^~^~^`\\                  /~^~^~^~^
                  ~^~^~^ `----------------' ~^~^~^
                 ~^~^~^~^~^^~^~^~^~^~^~^~^~^~^~^~""";

        Bukkit.getLogger().info(string);
    }

    public static void LogLittleBird(){
        String string = """

                   .-.
                  /'v'\\
                 (/   \\)
                ='="="===<\s
                mrf|_|""";

        Bukkit.getLogger().info(string);
    }
    public static void LogLoveBirds(){
        String string = """

                                 _  _
                                ( \\/ )
                         .---.   \\  /   .-"-.\s
                        /   6_6   \\/   / 4 4 \\
                        \\_  (__\\       \\_ v _/
                        //   \\\\        //   \\\\
                       ((     ))      ((     ))
                jgs=====""===""========""===""=======
                          |||            |||
                           |              |""";

        Bukkit.getLogger().info(string);
    }

    public static void LogSingingBirds(){
        String string = """

                             )   \s
                             \\   )  \s
                             ()  \\                           )
                                 ()                       )  \\
                                       .-""\"-.            \\  ()
                              ____    /  __   `\\     __   ()
                           .'`  __'. | o/__\\o   |   / /|
                          /  o /__\\o;\\  \\\\//   /_  // /
                 ._      _|    \\\\// |`-.__.-'|\\  `;  /
                /  \\   .'  \\-.____.'|   ||   |/    \\/
                `._ '-/             |   ||   '.___./
                .  '-.\\_.-'      __.'-._||_.-' _ /
                .`""===(||).___.(||)(||)----'(||)===...__
                 `"jgs"`""=====""\""========""\"====...__  `""==._
                                                       `"=.     `"=.
                                                           `"=.""";

        Bukkit.getLogger().info(string);
    }

    public static void LogCat(){
        String string = """

                 _._     _,-'""`-._
                (,-.`._,'(       |\\`-/|
                    `-.-' \\ )-`( , o o)
                          `-    \\`_`"'-""";

        Bukkit.getLogger().info(string);
    }

    public static void LogFatCat(){
        String string = """

                    /\\_____/\\
                   /  o   o  \\
                  ( ==  ^  == )
                   )         (
                  (           )
                 ( (  )   (  ) )
                (__(__)___(__)__)""";

        Bukkit.getLogger().info(string);
    }

    public static void LogCatBox(){
        String string = """

                  ,-.       _,---._ __  / \\
                 /  )    .-'       `./ /   \\
                (  (   ,'            `/    /|
                 \\  `-"             \\'\\   / |
                  `.              ,  \\ \\ /  |
                   /`.          ,'-`----Y   |
                  (            ;        |   '
                  |  ,-.    ,-'         |  /
                  |  | (   |        hjw | /
                  )  |  \\  `.___________|/
                  `--'   `--'""";

        Bukkit.getLogger().info(string);
    }

    public static void LogCatPainting(){
        String string = """

                .==============================================.
                |                                              |
                |                           .'\\                |
                |                          //  ;               |
                |                         /'   |               |
                |        .----..._    _../ |   \\               |
                |         \\'---._ `.-'      `  .'              |
                |          `.    '              `.             |
                |            :            _,.    '.            |
                |            |     ,_    (() '    |            |
                |            ;   .'(().  '      _/__..-        |
                |            \\ _ '       __  _.-'--._          |
                |            ,'.'...____'::-'  \\     `'        |
                |           / |   /         .---.              |
                |     .-.  '  '  / ,---.   (     )             |
                |    / /       ,' (     )---`-`-`-.._          |
                |   : '       /  '-`-`-`..........--'\\         |
                |   ' :      /  /                     '.       |
                |   :  \\    |  .'         o             \\      |
                |    \\  '  .' /          o       .       '     |
                |     \\  `.|  :      ,    : _o--'.\\      |     |
                |      `. /  '       ))    (   )  \\>     |     |
                |        ;   |      ((      \\ /    \\___  |     |
                |        ;   |      _))      `'.-'. ,-'` '     |
                |        |    `.   ((`            |/    /      |
                |        \\     ).  .))            '    .       |
                |     ----`-'-'  `''.::.________:::mx'' ---    |
                |                                              |
                |                                              |
                |                                              |
                '=============================================='""";

        Bukkit.getLogger().info(string);
    }

    public static void LogPacman() {
        String string = """

                __________________|      |____________________________________________
                     ,--.    ,--.          ,--.   ,--.
                    |oo  | _  \\  `.       | oo | |  oo|
                o  o|~~  |(_) /   ;       | ~~ | |  ~~|o  o  o  o  o  o  o  o  o  o  o
                    |/\\/\\|   '._,'        |/\\/\\| |/\\/\\|
                __________________        ____________________________________________
                                  |      |dwb""";

        Bukkit.getLogger().info(string);
    }
    public static void LogBisasam(){
        String string = """

                                                           /
                                        _,.------....___,.' ',.-.
                                     ,-'          _,.--"        |
                                   ,'         _.-'              .
                                  /   ,     ,'                   `
                                 .   /     /                     ``.
                                 |  |     .                       \\.\\
                       ____      |___._.  |       __               \\ `.
                     .'    `---""       ``"-.--"'`  \\               .  \\
                    .  ,            __               `              |   .
                    `,'         ,-"'  .               \\             |    L
                   ,'          '    _.'                -._          /    |
                  ,`-.    ,".   `--'                      >.      ,'     |
                 . .'\\'   `-'       __    ,  ,-.         /  `.__.-      ,'
                 ||:, .           ,'  ;  /  / \\ `        `.    .      .'/
                 j|:D  \\          `--'  ' ,'_  . .         `.__, \\   , /
                / L:_  |                 .  "' :_;                `.'.'
                .    ""'                  ""\"""'                    V
                 `.                                 .    `.   _,..  `
                   `,_   .    .                _,-'/    .. `,'   __  `
                    ) \\`._        ___....----"'  ,'   .'  \\ |   '  \\  .
                   /   `. "`-.--"'         _,' ,'     `---' |    `./  |
                  .   _  `""'--.._____..--"   ,             '         |
                  | ." `. `-.                /-.           /          ,
                  | `._.'    `,_            ;  /         ,'          .
                 .'          /| `-.        . ,'         ,           ,
                 '-.__ __ _,','    '`-..___;-...__   ,.'\\ ____.___.'
                 `"^--'..'   '-`-^-'"--    `-^-'`.''""\"""`.,^.`.--' mh""";

        Bukkit.getLogger().info(string);
    }

    public static void LogUnicorn(){
        String string = """

                \\.
                 \\\\      .
                  \\\\ _,.+;)_
                  .\\\\;~%:88%%.
                 (( a   `)9,8;%.
                 /`   _) ' `9%%%?
                (' .-' j    '8%%'
                 `"+   |    .88%)+._____..,,_   ,+%$%.
                       :.   d%9`             `-%*'"'~%$.
                    ___(   (%C                 `.   68%%9
                  ."        \\7                  ;  C8%%)`
                  : ."-.__,'.____________..,`   L.  \\86' ,
                  : L    : :            `  .'\\.   '.  %$9%)
                  ;  -.  : |             \\  \\  "-._ `. `~"
                   `. !  : |              )  >     ". ?
                     `'  : |            .' .'       : |
                         ; !          .' .'         : |
                        ,' ;         ' .'           ; (
                       .  (         j  (            `  \\
                       ""\"'          ""'             `"" mh""";

        Bukkit.getLogger().info(string);
    }

    public static void LogTest(){
        String string = "";

        Bukkit.getLogger().info(string);
    }
}
