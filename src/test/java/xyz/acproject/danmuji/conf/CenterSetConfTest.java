package xyz.acproject.danmuji.conf;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.acproject.danmuji.conf.set.ThankFollowSetConf;

/**
 * @author Tatooi
 * @since 2.6.41
 */
class CenterSetConfTest {

    @Test
    void normalCaseForOf() {
        CenterSetConf cen = CenterSetConf.of("8O@2zazB6D{3[DI3y<]Sz~@q6O!w!3!I!CBP~SNF7C>95tgB53!wzCcI6S?I!CBP~SZmz<U3[Cz25a)B$f@P7bcq7~^3[0hI!D]F5<?3[0hK^aqI!CcR7bZ9zSBC7f!w8O@F6RZL6b>K!0FCy<NPz`m36CZL5>ZFzf!w!3!I!D]F5<?3[3!O^PEq)|Em^f@Z$f@27~]L~t)27C>96S>q!0FCy<NPz`m3yCN2ySI3[DI3y<NI!0FCy<NPz`m3y~>q5RZOz~gI8`!wzCcI6S?I!CzR8DFV~tcRz~@V!0FCy<NPz`m35Ccez~^3[Be7$f@q4bcK4RZC5SNI5t63[Cz25a)B$f@q4bcK4RZD4<zq!0FCy<NPz`m37b225Ce97S>IySZez`!wzCcI6S?I!D>Fza^3[Be79`m3ySNLySe94<U3[DI3yCcO6CcDz`!w!K8eLK<!If!I!CBP~SZmz<U3[Cz25a)B$f@q4<RB!0E3^.hw^P?w^.h39`m3zCZI5bZt!0FX!C]B5bcV7bBez`!w^OUm$f@C5SNI5t7?4bcK4O!wzCcI6S?I!CzL5bNL7t^3[3$EI#$EI#!B7?V25<>P@8842[<cI+4PGaU3$f@F6RZI4~zB~SZmz<U3[Cz25a)B$f@F6RZL6b>K!0FCy<NPz`m34~)97a296S2Fz<NA!0FCy<NPz`m35D>e!0EN9`m34~)9y~>q5O!wzCcI6S?I!CBP~S@26D@2zS?3[D]O7<?I!CBP~S@26D@2zS>9y<V04bZO~t)E4<>Izf!wzCcI6S?I!CBP~S@26D@2zS>9zt>26C{3[D]O7<?I!CBP~S@26D@2zS>95<cKy<7B63!w7a@Rz`m34~)9yCcO6CcDz>Zez<]25f!wzCcI6S?I!CBP~S@26D@2zS>97<m3[Cz25a)B$f@F6RZ3y~@Oy<7B~tzF6f!w7a@Rz`m34~)9yCNLySI3[D]O7<?I!CBP~S)ezf!w7a@Rz`m34~)9zbZP4<7K!0FCy<NPz`m34~)9zCZI5bZt~S]e!0Fq6D>B$f@F6RZD4<zq!0Fq6D>B$f@F6RZD4<zq~SzOz<?3[D]O7<?I!CBP~SR25CcDz~@95bZD4<U3[Cz25a)B$f@F6RZL5CNF5C?3[Cz25a)B$f@F6RZP4f!wzCcI6S?I!CBP~t7B5b)L5<>9y<NI!0FCy<NPz`m34~)97S>IySZez>ZVz`!w7a@Rz`m35bZD!0FCy<NPz`m35<cKy<7B6BZHz~A3[3!O^.@0y0AS^Cc0)|Am)P>3[|yqy0ht^|?Oz.!P)b!t^f!I!CR25CcDz~@95<cU?SBwz`!w^|hI!DgO4~z2ytA3[DI3ySNLySe@5A]28`!w^fm34~)95tgB53!wzCcI6S?I!D)FzSVdy~A3[0hI!D)ey<NI~S2By~@q~t>O5f!w!C2q7ahw$OZ34<NF4b>26D{e^`VEz~@L4t>26ahKySZe$S>KyO@Z$f@Oz~gI8`!w8O@27~]L?C>m5aB|z~]P!0F58O@F6RZ2yS)R6Ccqz`!wzCcI6S?I!CBP~SZmz<U3[D]O7<?I!CeB8~7L6C]P!0F5!K<6G[<VIK`X1[`V3aNYVzOEV5COVz359aPBD#0B1zHAKU.AKy2Y9[<gCK`X1[`V3[<6Gf@7$f@Oz~gI8`!w!K4)E+CYA+81e[8HC8<YK8<VB8<6G.V9}f!I!D)E4<>Iza^3[Be79>qI!CBP~SNF7C>95tgB53!w7a@Rz`m34~)95tgB53!w7a@Rz`m35bBP7cZmz<Zm5b>96S2Fz<NA~t)qy~]R6O!w^fm37bBez`!w^fUm9`m36CZL5<BA!0Et).1R).?m$f@P4<7K~t]F5<?3[3!m^.EP^.Em^f!I!D]Ey<VH~S7FzD{3[DI3ySZAz>)q6CBKzt^3[Be7$f@Az<N28~]F5<?3[0^K^fm3zSBC7c)q6CBKzt^3[Be7$f@D4<zq>b225CI3[Cz25a)B$f@F6RZD4<zq~S)Lzb?3[Cz25a)B$f@F6RZD7<cOzcZI5S)25f!wzCcI6S?I!CBP~S7Ry~@A~t@B6bZO7f!wzCcI6S?I!CBP~SNF7C>95tgB53!wzCcI6S?I!CBP~SZmz<U3[Cz25a)B$f@F6RZq8cZP4bBB5b{3[Cz25a)B$f@I4~)q~S7FzD]96S2Fz<NA~t)qy~]R6O!w^fm35bBP7cZmz<Zm5b>96S2Fz<NA~t)qy~]R6O!w^fm35D>e!0EN$f@Oz~gL6D{3[3!3$f@Oz~gL6D]9yCcO6CcDz`!w!3!I!D)E4<>IzcZP7bcq7~^3[0hI!D]Ey<VH!0E3VE`9w$f3@~>[y<RB@8<AF+`ZHf>?8~gB@8842f>a4<zq|Ccez`?18f>[7<qB93!I!D]Ey<VH]SBC7c@R5b>|z~]P!0F5~`m37b225Ce96t]27a>P!0Em9`m37S>IySZez`!w8O@Az<N28~]F5<?3[0^K^fm34~)95bBSz>ZL6b>K!0Fq6D>B$f@F6RZL6b>K!0Fq6D>B$f@F6RZq8cZP4bBB5b{3[Cz25a)B$f@I4~)q~tgB5tgIz>ZP4bBB5b]96t]27a>P!0Em$f@K7<q3[0dI!D7B5b)L5<>?4bcK4O!wzCcI6S?I!D7B5b)L5<>P!0E3VGO3w$+[@~>[y<RB6O~ELVLB24~DCX|CAGtFBX]+!DqI!D7F5BZ27~]L~SZmz<V|z~{3[D]O7<>Z");
        Assertions.assertThat(cen)
                .extracting(CenterSetConf::is_auto, CenterSetConf::is_block, CenterSetConf::getFollow)
                .containsExactly(false, true, new ThankFollowSetConf());
    }

    @Test
    void emptyParamCaseForOf() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> CenterSetConf.of(null));
        CenterSetConf cen1 = CenterSetConf.of("");
        org.junit.jupiter.api.Assertions.assertNull(cen1);
        CenterSetConf cen2 = CenterSetConf.of("{}");
        org.junit.jupiter.api.Assertions.assertNotNull(cen2);

    }
}