public class resources {
	
	//Syllabary Table
	public final String[][] syllabary = {
			{"a","あ","ア"},	{"i","い","イ"},	{"u","う","ウ"},	{"e","え","エ"},	{"o","お","オ"},
			{"ka","か","カ"},{"ki","き","キ"},{"ku","く","ク"},{"ke","け","ケ"},{"ko","こ","コ"},
			{"sa","さ","サ"},{"shi","し","シ"},{"su","す","ス"},{"se","せ","セ"},{"so","そ","ソ"},
			{"ta","た","タ"},{"chi","ち","チ"},{"tsu","つ","ツ"},{"te","て","テ"},{"to","と","ト"},
			{"na","な","ナ"},{"ni","に","ニ"},{"nu","ぬ","ヌ"},{"ne","ね","ネ"},{"no","の","ノ"},
			{"ha","は","ハ"},{"hi","ひ","ヒ"},{"fu","ふ","フ"},{"he","へ","ヘ"},{"ho","ほ","ホ"},
			{"ma","ま","マ"},{"mi","み","ミ"},{"mu","む","ム"},{"me","め","メ"},	{"mo","も","モ"},
			{"ya","や","ヤ"},{"i","い","イ"},{"yu","ゆ","ユ"},{"e","え","エ"},{"yo","よ","ヨ"},
			{"ra","ら","ラ"},{"ri","り","リ"},{"ru","る","ル"},{"re","れ","レ"},{"ro","ろ","ロ"},
			{"wa","わ","ワ"},{"i","い","イ"},	{"u","う","ウ"},	{"e","え","エ"},{"o","を","ヲ"},
			{"n","ん","ン"},
			{"kya","きゃ","キャ"},{"kyu","きゅ","キュ"},{"kyo","きょ","キョ"},
			{"sha","しゃ","シャ"},{"shu","しゅ","シュ"},{"sho","しょ","ショ"},
			{"cha","ちゃ","チャ"},{"chu","ちゅ","チュ"},{"cho","ちょ","チョ"},
			{"nya","にゃ","ニャ"},{"nyu","にゅ","ニュ"},{"nyo","にょ","ニョ"},
			{"hya","ひゃ","ヒャ"},{"hyu","ひゅ","ヒュ"},{"hyo","ひょ","ヒョ"},
			{"mya","みゃ","ミャ"},{"myu","みゅ","ミュ"},{"myo","みょ","ミョ"},
			{"rya","りゃ","リャ"},{"ryu","りゅ","リュ"},{"ryo","りょ","リョ"},
			{"ga","が","ガ"},{"gi","ぎ","ギ"},{"gu","ぐ","グ"},{"ge","げ","ゲ"},{"go","ご","ゴ"},
			{"za","ざ","ザ"},{"ji","じ","ジ"},{"zu","ず","ズ"},{"ze","ぜ","ゼ"},{"zo","ぞ","ゾ"},
			{"da","だ","ダ"},{"ji","ぢ","ヂ"},{"zu","づ","ヅ"},{"de","で","デ"},{"do","ど","ド"},	
			{"ba","ば","バ"},{"bi","び","ビ"},{"bu","ぶ","ブ"},{"be","べ","ベ"},{"bo","ぼ","ボ"},
			{"pa","ぱ","パ"},{"pi","ぴ","ピ"},{"pu","ぷ","プ"},{"pe","ぺ","ペ"},{"po","ぽ","ポ"},
			{"gya","ぎゃ","ギャ"},{"gyu","ぎゅ","ギュ"},{"gyo","ぎょ","ギョ"},
			{"ja","じゃ","ジャ"},{"ju","じゅ","ジュ"},{"jo","じょ","ジョ"},
			{"bya","びゃ","ビャ"},{"byu","びゅ","ビュ"},{"byo","びょ","ビョ"},
			{"pya","ぴゃ","ピャ"},{"pyu","ぴゅ","ピュ"},{"pyo","ぴょ","ピョ"} };
	//End of Syllabary Table
	
	//Sound File Information
	public final String soundFolder = "voice/";
	public final String soundExtension = ".mp3";
	//End of Sound File Information
	
	//Help Menu Information
	public final String instruction, about;
	//End of Help Menu Information
	
	//Constructor
	resources()
	{
		instruction = "<html><font size=\"4\">1. Unzip the program package and make sure the Main program resides in the SAME folder with the SETTINGS.INI (if exists) and the VIOCE folder.</font></html>\n"
				    + "<html><font size=\"4\">2. The \"FROM\" field should be less than the \"TO\" field, and they both need to be in the range between Min-Max.</font></html>\n"
					+ "<html><font size=\"4\">3. \"Reset Program\" won't overwrite your saved settings, only \"Save Progress\" will.</font></html>\n"
				    + "<html><font size=\"4\">4. In the \"View Syllable Chart\" window, you can CLICK EACH SYLLABLE TO HEAR ITS PRONUNCIATION.</font></html>\n";
		about = "<html><font size=\"4\">\"Japanese Syllable Trainer\" is designed by Alexander Chen.</font></html>\n"
				+ "<html><font size=\"4\">Thanks for using.</font></html>";
	}
	//End of Constructor
}
