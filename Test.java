import java.io.*;
import java.util.Locale;
class OutputProcessor implements Runnable {
    private InputStream inputStream;
    private boolean isSilence;

    //是否打印
    public OutputProcessor(InputStream inputStream, boolean isSilence) {
        this.inputStream = inputStream;
        this.isSilence = isSilence;
    }

    //默认是silence, 不打印任何收到的内容
    public OutputProcessor(InputStream inputStream) {
        this(inputStream, true);
    }

    @Override
    public void run() {
        try(InputStreamReader isr = new InputStreamReader(inputStream);
            LineNumberReader br = new LineNumberReader(isr);
            //BufferedReader br = new BufferedReader(isr);
        ) {
            String line;
            if(!isSilence) {
                while ((line = br.readLine()) != null) {
                        System.out.println(br.getLineNumber() + ": " + line);
                }
            } else {
                while ((line = br.readLine()) != null) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Test {
    private static boolean isWindows;
    private static boolean isLinux;
    static {
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("windows")) {
            isWindows = true;
            isLinux = false;
        }else if(os.contains("linux")) {
            isWindows = true;
            isLinux = false;
        } else {
            isWindows = false;
            isLinux = false;
        }
    }

    public static int execCommand(String command) {
        int retCode = 0;
        try {
            System.out.println("Creating Process...");
            Process p = Runtime.getRuntime().exec(command);
            new Thread(new OutputProcessor(p.getErrorStream())).start();
            new Thread(new OutputProcessor(p.getInputStream())).start();
            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
            retCode = -1;
        }
        return retCode;
    }

    public static void writeFile(String fileName, String fileContent) {
        try(PrintWriter pr = new PrintWriter(new FileWriter(fileName))) {
            pr.println(fileContent);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateTexStyFile(String filePath) {
        String sty = "\\NeedsTeXFormat{LaTeX2e}[2005/12/01]\n\\ProvidesPackage{YYbook}[2020/02/11]\n\\RequirePackage[a4paper,margin=2.5cm,bottom=1.5cm]{geometry}\n\\RequirePackage{fancyhdr,fancybox}\n\\RequirePackage{ifthen}\n\\RequirePackage{lastpage}\n\\RequirePackage{paralist}\n\\RequirePackage{indentfirst}\n\\RequirePackage[toc,page,title,titletoc,header]{appendix}\n\\setcounter{tocdepth}{3}\n\\RequirePackage{lipsum}\n\\RequirePackage{mathtools} \n\\RequirePackage{wasysym}\n\\RequirePackage{pdfpages}\n\\RequirePackage{graphicx}\n\\RequirePackage{subfigure}\n\\RequirePackage{physics,amsfonts,bm,amsthm,amscd}\n\\RequirePackage{tensor}\n\\RequirePackage{charter}\n\\RequirePackage{palatino}\n\\RequirePackage{mathrsfs}\n\\RequirePackage{amsmath,amssymb}\n\\newtheorem{Theorem}{Theorem}[section]\n\\newtheorem{Lemma}{Lemma}[section]\n\\newtheorem{Corollary}{Corollary}[section]\n\\newtheorem{Proposition}{Proposition}[section]\n\\newtheorem{Definition}{Definition}[section]\n\\newtheorem{Example}{Example}[section]\n\\RequirePackage{titlesec,titletoc}\n\\setcounter{tocdepth}{3}\n\\RequirePackage[font=small]{caption}\n\\renewcommand\\abstractname{Summary}\n\\RequirePackage{longtable,multirow,array}\n\\RequirePackage{booktabs}\n\n\\RequirePackage[T1]{fontenc}\n\\RequirePackage{url}\n\\def\\UrlBreaks{\\do\\A\\do\\B\\do\\C\\do\\D\\do\\E\\do\\F\\do\\G\\do\\H\\do\\I\\do\\J\n\\do\\K\\do\\L\\do\\M\\do\\N\\do\\O\\do\\P\\do\\Q\\do\\R\\do\\S\\do\\T\\do\\U\\do\\V\n\\do\\W\\do\\X\\do\\Y\\do\\Z\\do\\[\\do\\\\\\do\\]\\do\\^\\do\\_\\do\\`\\do\\a\\do\\b\n\\do\\c\\do\\d\\do\\e\\do\\f\\do\\g\\do\\h\\do\\i\\do\\j\\do\\k\\do\\l\\do\\m\\do\\n\n\\do\\o\\do\\p\\do\\q\\do\\r\\do\\s\\do\\t\\do\\u\\do\\v\\do\\w\\do\\x\\do\\y\\do\\z\n\\do\\.\\do\\@\\do\\\\\\do\\/\\do\\!\\do\\_\\do\\|\\do\\;\\do\\>\\do\\]\\do\\)\\do\\,\n\\do\\?\\do\\'\\do+\\do\\=\\do\\#}\n\\setlength{\\headheight}{15pt}\n\\newcommand{\\MCM@control}{0000000}\n\\DeclareOption*{\\edef\\MCM@control{\\CurrentOption}}\n\\ProcessOptions\n\\newcommand{\\control}{\\MCM@control}\n\\newcommand{\\team}{\\MCM@control}\n\\newcommand{\\contest}{MCM/ICM}\n\n\\RequirePackage{graphicx}\n\\RequirePackage{flafter}\n\\RequirePackage{ifpdf}\n\\ifpdf\n\\RequirePackage{epstopdf}\n\\DeclareGraphicsExtensions{.pdf,.jpg,.jpeg,.png}\n\\RequirePackage[\n    linkcolor=black,  % 消除链接色彩\n    citecolor=black,\n    colorlinks=true,\n    linkcolor=black,\n    citecolor=black,\n    urlcolor=black]{hyperref}\n\\else\\DeclareGraphicsExtensions{.eps,.ps}\n\\ifxetex\\RequirePackage[\n    xetex,  % 运行 xetex\n    pdfstartview=FitH,\n    bookmarksnumbered=true,\n    bookmarksopen=true,\n    colorlinks=true,\n    linkcolor=black,\n    citecolor=black,\n    urlcolor=black]{hyperref}\n\\else\\RequirePackage[\n    dvipdfm,  % 运行其他编译引擎（如 luatex）\n    pdfstartview=FitH,\n    bookmarksnumbered=true,\n    bookmarksopen=true,\n    colorlinks=true,\n    linkcolor=black,\n    citecolor=black,\n    urlcolor=black]{hyperref}\n\\fi\\fi\n\\RequirePackage{algorithm} \n\\RequirePackage{algpseudocode}\n\\RequirePackage{algorithmicx}\n\\floatname{algorithm}{Algorithm}  \n\\renewcommand{\\algorithmicrequire}{\\textbf{Input:}}  \n\\renewcommand{\\algorithmicensure}{\\textbf{Output:}} \n\\RequirePackage{listings,xcolor}  \n\\definecolor{grey}{rgb}{0.8,0.8,0.8}\n\\definecolor{darkgreen}{rgb}{0,0.3,0}\n\\definecolor{darkblue}{rgb}{0,0,0.3}\n\\def\\lstbasicfont{\\fontfamily{pcr}\\selectfont\\footnotesize}\n\\lstset{\n    showstringspaces=false,\n    showspaces=false,%\n    tabsize=4,%\n    frame=lines,%\n    basicstyle={\\footnotesize\\lstbasicfont},%\n    keywordstyle=\\color{darkblue}\\bfseries,%\n    identifierstyle=,%\n    commentstyle=\\color{darkgreen},%\\itshape,%\n    stringstyle=\\color{black}%\n}\n\\lstloadlanguages{C,C++,Java,Matlab,Mathematica,Python}\n\\lstnewenvironment{mat}\n{\\lstset{language=mathematica,mathescape,columns=flexible,frame=lines}}\n{}\n\\setcounter{totalnumber}{6}\n\\setcounter{topnumber}{3}\n\\setcounter{bottomnumber}{3}\n\\renewcommand{\\textfraction}{0.15}\n\\renewcommand{\\topfraction}{0.85}\n\\renewcommand{\\bottomfraction}{0.65}\n\\renewcommand{\\floatpagefraction}{0.60}\n\\renewcommand{\\figurename}{Figure}\n\\renewcommand{\\tablename}{Table}\n\\setlength{\\belowcaptionskip}{4pt}\n\\setlength{\\abovecaptionskip}{4pt}\n\\graphicspath{{./}{./img/}{./fig/}{./image/}{./figure/}{./picture/}}\n\\lhead{\\small \\team}\n\\chead{}\n\\rhead{\\small Page \\thepage\\ of \\pageref{LastPage}}\n\\lfoot{}\n\\cfoot{}\n\\rfoot{}\n\\newcounter{prefix}\n\\renewcommand{\\theHsection}{\\theprefix.\\thesection}\n\\newenvironment{letter}[1]{\\refstepcounter{section}\\addtocounter{section}{-1}\\section*{#1}\\addcontentsline{toc}{section}{#1}}{\\stepcounter{prefix}}\n\\RequirePackage{etoolbox}\n\\AtBeginEnvironment{abstract}{\\setlength\\parskip{1ex}}\n\\AtBeginEnvironment{thebibliography}{\n    \\refstepcounter{section}\n    \\addcontentsline{toc}{section}{References}}\n\\BeforeBeginEnvironment{subappendices}{\n    \\clearpage\n    \\setcounter{secnumdepth}{-1}}\n\\BeforeBeginEnvironment{letter}{\\clearpage}\n\n\n\\newcommand{\\@problem}[1]{}\n\\newcommand{\\problem}[1]{\\gdef\\@problem{#1}}\n\\newcommand{\\makesheet}{ \n    \\null%\n    \\vspace*{-16ex}%\n	\\centerline{\\begin{tabular}{*3{c}}\n		\\parbox[t]{0.3\\linewidth}{\\begin{center}\\textbf{Problem Chosen}\\\\ \\Large \\textcolor{red}{\\@problem}\\end{center}}\n		& \\parbox[t]{0.3\\linewidth}{\\begin{center}\\textbf{2020\\\\ \\contest\\\\ Summary Sheet}\\end{center}}\n		& \\parbox[t]{0.3\\linewidth}{\\begin{center}\\textbf{Team Control Number}\\\\ \\Large \\textcolor{red}{\\MCM@control}\\end{center}}	\\\\\n		\\hline\n	\\end{tabular}}\n	\\vskip 30pt\n}\n\n\\newbox\\@abstract\n\\setbox\\@abstract\\hbox{}\n\\long\\def\\abstract{\\bgroup\\global\\setbox\\@abstract\\vbox\\bgroup\\hsize\\textwidth}\n\\def\\endabstract{\\egroup\\egroup}\n\\def\\make@abstract{\n    \\vskip -10pt\\par\n    {\\centering\\Large\\bfseries\\@title\\vskip1ex}\\par \n    {\\centering\\bfseries\\abstractname\\vskip1.5ex}\\par \n    \\noindent\\usebox\\@abstract\\par\n    \\vskip 10pt}\n\n\n\\def\\@maketitle{\n	\\makesheet%\n	\\make@abstract\n    \\pagenumbering{gobble}\n    \\pagestyle{empty}\n    \\newpage\n    \\pagenumbering{arabic}\n    \\setcounter{page}{1}}\n\n\\renewcommand\\tableofcontents{%\n    \\centerline{\\normalfont\\Large\\bfseries\\contentsname%\n    \\@mkboth{%\n    \\MakeUppercase\\contentsname}{\\MakeUppercase\\contentsname}}%\n    \\vskip 3ex%\n    \\@starttoc{toc}%\n    \\thispagestyle{empty}\n    \\clearpage\n    \\pagestyle{fancy}\n    \\setlength\\parskip{1ex}}\n\\linespread{0.8}\n\\usepackage{ctex}\n\\CTEXoptions[today=old]\n\n\\endinput";
        writeFile(filePath + "/YYbook.sty", sty);
    }

    public static void generateMainTexFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        sb.append("\\documentclass[12pt]{article}\n\\usepackage[YongYANG]{YYbook}\n");
        sb.append("\\begin{document}\n");
        sb.append("\\begin{titlepage}\\Large\n");
        sb.append("\\renewcommand{\\thefootnote}{\\fnsymbol{footnote}}\n");
        sb.append("\\begin{center}\n");
        sb.append("\\vspace*{3cm}\n");
        sb.append("{\\Huge\\color{blue} Notes}\n\n \\bigskip\n");
        sb.append("{\\LARGE Yong YANG\\footnote{{\\large \\url{https://bupt-yy.github.io}}}}\n\n\\medskip\n");
        sb.append("\\today\n");
        sb.append("\\end{center}\n");
        sb.append("\\end{titlepage}\n");
        sb.append("\\clearpage\n");
        sb.append("\\tableofcontents  % 生成目录\n");
        sb.append("\\section{Section One}\n");
        sb.append("Hello, world!\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\\end{document}  % 结束\n");

        writeFile(filePath + "/main.tex", sb.toString());
    }

    public static void generateBatFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        sb.append("cd \"" + filePath  +  "\"\n");
        sb.append("xelatex.exe -synctex=1 -interaction=nonstopmode \"main\".tex\n");
        sb.append("xelatex.exe -synctex=1 -interaction=nonstopmode \"main\".tex\n");
        sb.append("\n");

        writeFile(filePath + "/makeTeX.bat", sb.toString());
    }

    public static void main(String[] args) {
        String folderPath = "C:/Users/94584/IdeaProjects/Snake/doc/tex";
        generateTexStyFile(folderPath);
        generateMainTexFile(folderPath);
        generateBatFile(folderPath);
        execCommand(folderPath + "/makeTeX.bat");
    }
}
