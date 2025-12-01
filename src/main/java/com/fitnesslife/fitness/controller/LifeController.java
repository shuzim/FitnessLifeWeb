package com.fitnesslife.fitness.controller;

import com.fitnesslife.fitness.model.Usuario;
import com.fitnesslife.fitness.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class LifeController {

    private static final Logger logger = LoggerFactory.getLogger(LifeController.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/")
    public String home() {
        logger.info("Acessando a página inicial, redirecionando para /login");
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String welcome() {
        logger.info("Acessando a página de login");
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        logger.info("Acessando a página de cadastro");
        return "cadastro";
    }

    @GetMapping("/menu")
    public String menu() {
        logger.info("Acessando a página de menu");
        return "menu";
    }



    @GetMapping("/perfilnutricional")
    public String perfilNutricional(Model model) {
        logger.info("Acessando a página de perfil nutricional");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            String email = auth.getName();
            logger.info("Buscando usuário com email: {}", email);
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
            if (usuarioOpt.isPresent()) {
                model.addAttribute("usuario", usuarioOpt.get());
                logger.info("Usuário encontrado: {}", email);
            } else {
                logger.warn("Usuário não encontrado: {}", email);
            }
        } else {
            logger.warn("Nenhum usuário autenticado encontrado");
        }
        return "perfilNutricional";
    }

    @GetMapping("/planorefeicoes")
    public String planoRefeicoes(Model model) {
        logger.info("Acessando a página de plano de refeições");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            String email = auth.getName();
            logger.info("Buscando usuário com email: {}", email);
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                model.addAttribute("usuario", usuario);

                // Determinar o plano de refeição com base no peso e idade
                String planoRefeicao;
                double peso = usuario.getPeso();
                int idade = usuario.getIdade();

                if (peso >= 30 && peso <= 50) {
                    if (idade >= 18 && idade <= 30) {
                        planoRefeicao = "<h5>Café da Manhã: Mingau de Aveia com Banana (400 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1/2 xícara de aveia em flocos</li><li>1 xícara de leite integral</li><li>1 banana madura</li><li>1 colher de sopa de mel</li><li>10 g de castanhas</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Aqueça o leite em uma panela em fogo médio.</li>" +
                                "<li>Adicione a aveia e cozinhe por 5 minutos, mexendo até engrossar.</li>" +
                                "<li>Corte a banana em rodelas e misture ao mingau.</li>" +
                                "<li>Adicione o mel e as castanhas por cima.</li>" +
                                "<li>Sirva quente.</li></ol>" +
                                "<h5>Almoço: Frango com Arroz Integral e Brócolis (500 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de peito de frango</li><li>1/2 xícara de arroz integral cozido</li><li>1 xícara de brócolis cozido</li><li>1 colher de chá de azeite</li><li>Sal e pimenta a gosto</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o frango com sal e pimenta.</li>" +
                                "<li>Grelhe o frango em uma frigideira com azeite por 5-7 minutos de cada lado.</li>" +
                                "<li>Cozinhe o arroz integral conforme instruções da embalagem.</li>" +
                                "<li>Cozinhe o brócolis no vapor por 5 minutos.</li>" +
                                "<li>Sirva o frango com o arroz e o brócolis.</li></ol>" +
                                "<h5>Lanche: Iogurte com Granola (200 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1 potes de iogurte natural (150 g)</li><li>2 colheres de sopa de granola</li><li>1/2 xícara de frutas frescas</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Misture o iogurte com a granola em uma tigela.</li>" +
                                "<li>Adicione as frutas frescas por cima.</li>" +
                                "<li>Sirva imediatamente.</li></ol>" +
                                "<h5>Jantar: Peixe Assado com Batata-Doce (400 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de filé de tilápia</li><li>1 batata-doce pequena</li><li>1 xícara de salada de folhas verdes</li><li>1 colher de chá de azeite</li><li>Sal e limão a gosto</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o peixe com sal e limão.</li>" +
                                "<li>Asse o peixe a 180°C por 15 minutos.</li>" +
                                "<li>Asse a batata-doce a 200°C por 30 minutos ou até ficar macia.</li>" +
                                "<li>Sirva com a salada temperada com azeite.</li></ol>";
                    } else if (idade >= 31 && idade <= 50) {
                        planoRefeicao = "<h5>Café da Manhã: Pão Integral com Pasta de Amendoim (350 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>2 fatias de pão integral</li><li>1 colher de sopa de pasta de amendoim</li><li>1/2 xícara de suco de laranja natural</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Toste as fatias de pão integral.</li>" +
                                "<li>Espalhe a pasta de amendoim sobre o pão.</li>" +
                                "<li>Sirva com o suco de laranja.</li></ol>" +
                                "<h5>Almoço: Frango com Arroz e Legumes (450 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de peito de frango</li><li>1/2 xícara de arroz integral cozido</li><li>1/2 xícara de cenoura refogada</li><li>1/2 xícara de abobrinha refogada</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o frango com sal e pimenta.</li>" +
                                "<li>Grelhe o frango com azeite por 5-7 minutos de cada lado.</li>" +
                                "<li>Refogue a cenoura e a abobrinha em uma frigideira com azeite.</li>" +
                                "<li>Sirva com o arroz integral.</li></ol>" +
                                "<h5>Lanche: Iogurte com Chia (150 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1 potes de iogurte desnatado (150 g)</li><li>1 colher de chá de chia</li><li>1 colher de chá de mel</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Misture o iogurte com a chia e o mel em uma tigela.</li>" +
                                "<li>Deixe descansar por 10 minutos para a chia hidratar.</li>" +
                                "<li>Sirva frio.</li></ol>" +
                                "<h5>Jantar: Tilápia com Purê de Batata (350 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de filé de tilápia</li><li>1 batata pequena</li><li>1 xícara de salada de tomate e pepino</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere a tilápia com sal e limão.</li>" +
                                "<li>Grelhe a tilápia por 4-5 minutos de cada lado.</li>" +
                                "<li>Cozinhe a batata até ficar macia e amasse com azeite.</li>" +
                                "<li>Sirva com a salada temperada.</li></ol>";
                    } else {
                        planoRefeicao = "<h5>Café da Manhã: Mingau de Aveia com Maçã (300 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1/3 xícara de aveia em flocos</li><li>1 xícara de leite desnatado</li><li>1 maçã fatiada</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Aqueça o leite em uma panela.</li>" +
                                "<li>Adicione a aveia e cozinhe por 5 minutos.</li>" +
                                "<li>Misture a maçã fatiada e sirva quente.</li></ol>" +
                                "<h5>Almoço: Frango com Espinafre (400 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de peito de frango</li><li>1/2 xícara de arroz integral cozido</li><li>1 xícara de espinafre cozido</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o frango com sal e pimenta.</li>" +
                                "<li>Grelhe o frango com azeite por 5-7 minutos.</li>" +
                                "<li>Cozinhe o espinafre no vapor por 3 minutos.</li>" +
                                "<li>Sirva com o arroz integral.</li></ol>" +
                                "<h5>Lanche: Frutas com Nozes (150 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1/2 xícara de frutas frescas</li><li>10 g de nozes</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Corte as frutas em pedaços.</li>" +
                                "<li>Misture com as nozes e sirva.</li></ol>" +
                                "<h5>Jantar: Peixe com Legumes (300 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de filé de peixe</li><li>1 xícara de legumes no vapor</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o peixe com sal e limão.</li>" +
                                "<li>Asse o peixe a 180°C por 15 minutos.</li>" +
                                "<li>Sirva com os legumes temperados com azeite.</li></ol>";
                    }
                } else if (peso >= 51 && peso <= 70) {
                    if (idade >= 18 && idade <= 30) {
                        planoRefeicao = "<h5>Café da Manhã: Omelete com Vegetais (450 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>2 ovos inteiros</li><li>1/4 xícara de pimentão vermelho picado</li><li>1/4 xícara de espinafre picado</li><li>1/4 xícara de cogumelos fatiados</li><li>Sal e pimenta a gosto</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Bata os ovos em uma tigela e tempere com sal e pimenta.</li>" +
                                "<li>Aqueça o azeite em uma frigideira antiaderente.</li>" +
                                "<li>Refogue os vegetais por 3-4 minutos até ficarem macios.</li>" +
                                "<li>Despeje os ovos sobre os vegetais e cozinhe até firmar.</li>" +
                                "<li>Dobre a omelete ao meio e sirva com uma fatia de pão integral.</li></ol>" +
                                "<h5>Almoço: Quinoa com Filé Mignon (600 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de filé mignon</li><li>1/2 xícara de quinoa cozida</li><li>1 xícara de salada de rúcula e tomate</li><li>1 colher de sopa de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o filé com sal e pimenta.</li>" +
                                "<li>Grelhe o filé por 4-5 minutos de cada lado.</li>" +
                                "<li>Cozinhe a quinoa conforme instruções da embalagem.</li>" +
                                "<li>Sirva com a salada temperada com azeite.</li></ol>" +
                                "<h5>Lanche: Shake de Proteína (250 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1 scoop de proteína em pó</li><li>1 xícara de leite desnatado</li><li>1/2 xícara de frutas vermelhas</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Misture todos os ingredientes no liquidificador.</li>" +
                                "<li>Bata até ficar homogêneo.</li>" +
                                "<li>Sirva gelado.</li></ol>" +
                                "<h5>Jantar: Salmão com Legumes (500 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de salmão</li><li>1 xícara de legumes no vapor</li><li>1/2 xícara de arroz integral cozido</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o salmão com sal e limão.</li>" +
                                "<li>Grelhe o salmão por 4-5 minutos de cada lado.</li>" +
                                "<li>Cozinhe os legumes no vapor por 5 minutos.</li>" +
                                "<li>Sirva com o arroz integral temperado com azeite.</li></ol>";
                    } else if (idade >= 31 && idade <= 50) {
                        planoRefeicao = "<h5>Café da Manhã: Torrada com Ricota (400 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>2 fatias de pão integral</li><li>2 colheres de sopa de ricota</li><li>1/2 xícara de suco de maçã</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Toste o pão integral.</li>" +
                                "<li>Espalhe a ricota sobre as fatias.</li>" +
                                "<li>Sirva com o suco de maçã.</li></ol>" +
                                "<h5>Almoço: Frango com Brócolis (550 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>120 g de peito de frango</li><li>1/2 xícara de arroz integral cozido</li><li>1 xícara de brócolis refogado</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o frango com sal e pimenta.</li>" +
                                "<li>Grelhe o frango com azeite por 5-7 minutos.</li>" +
                                "<li>Refogue o brócolis com azeite por 4 minutos.</li>" +
                                "<li>Sirva com o arroz integral.</li></ol>" +
                                "<h5>Lanche: Iogurte com Granola (200 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1 potes de iogurte natural (150 g)</li><li>2 colheres de sopa de granola</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Misture o iogurte com a granola.</li>" +
                                "<li>Sirva imediatamente.</li></ol>" +
                                "<h5>Jantar: Peixe com Batata-Doce (450 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de filé de peixe</li><li>1 batata-doce pequena</li><li>1 xícara de salada verde</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o peixe com sal e limão.</li>" +
                                "<li>Asse o peixe a 180°C por 15 minutos.</li>" +
                                "<li>Asse a batata-doce a 200°C por 30 minutos.</li>" +
                                "<li>Sirva com a salada temperada com azeite.</li></ol>";
                    } else {
                        planoRefeicao = "<h5>Café da Manhã: Aveia com Frutas (350 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1/3 xícara de aveia em flocos</li><li>1 xícara de leite desnatado</li><li>1/2 xícara de frutas frescas</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Aqueça o leite em uma panela.</li>" +
                                "<li>Adicione a aveia e cozinhe por 5 minutos.</li>" +
                                "<li>Misture as frutas frescas e sirva.</li></ol>" +
                                "<h5>Almoço: Peixe com Legumes (450 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de filé de peixe</li><li>1/2 xícara de arroz integral cozido</li><li>1 xícara de legumes refogados</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o peixe com sal e limão.</li>" +
                                "<li>Grelhe o peixe por 4-5 minutos de cada lado.</li>" +
                                "<li>Refogue os legumes com azeite por 5 minutos.</li>" +
                                "<li>Sirva com o arroz integral.</li></ol>" +
                                "<h5>Lanche: Nozes e Frutas (150 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>10 g de nozes</li><li>1/2 xícara de frutas frescas</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Misture as nozes com as frutas cortadas.</li>" +
                                "<li>Sirva imediatamente.</li></ol>" +
                                "<h5>Jantar: Omelete com Espinafre (350 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>2 ovos inteiros</li><li>1 xícara de espinafre picado</li><li>1 colher de chá de azeite</li><li>Sal e pimenta a gosto</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Bata os ovos e tempere com sal e pimenta.</li>" +
                                "<li>Aqueça o azeite em uma frigideira.</li>" +
                                "<li>Refogue o espinafre por 2 minutos.</li>" +
                                "<li>Despeje os ovos e cozinhe até firmar.</li>" +
                                "<li>Sirva com uma salada verde.</li></ol>";
                    }
                } else if (peso >= 71 && peso <= 90) {
                    if (idade >= 18 && idade <= 30) {
                        planoRefeicao = "<h5>Café da Manhã: Smoothie de Banana e Aveia (500 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1 banana madura</li><li>1/2 xícara de aveia</li><li>1 xícara de leite desnatado</li><li>1 colher de sopa de pasta de amendoim</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Misture todos os ingredientes no liquidificador.</li>" +
                                "<li>Bata até ficar homogêneo.</li>" +
                                "<li>Sirva gelado.</li></ol>" +
                                "<h5>Almoço: Frango com Abobrinha (650 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>120 g de peito de frango</li><li>1/2 xícara de arroz integral cozido</li><li>1 xícara de abobrinha refogada</li><li>1 xícara de cenoura refogada</li><li>1 colher de sopa de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o frango com sal e pimenta.</li>" +
                                "<li>Grelhe o frango com azeite por 5-7 minutos.</li>" +
                                "<li>Refogue a abobrinha e a cenoura com azeite por 5 minutos.</li>" +
                                "<li>Sirva com o arroz integral.</li></ol>" +
                                "<h5>Lanche: Sanduíche de Atum (300 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1 fatia de pão integral</li><li>50 g de atum em conserva</li><li>1/4 de abacate</li><li>Folhas de alface</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Escorra o atum e misture com o abacate amassado.</li>" +
                                "<li>Espalhe a mistura sobre o pão integral.</li>" +
                                "<li>Adicione alface e sirva.</li></ol>" +
                                "<h5>Jantar: Salmão com Quinoa (550 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>120 g de salmão</li><li>1/2 xícara de quinoa cozida</li><li>1 xícara de salada verde</li><li>1 colher de sopa de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o salmão com sal e limão.</li>" +
                                "<li>Grelhe o salmão por 4-5 minutos de cada lado.</li>" +
                                "<li>Cozinhe a quinoa conforme instruções.</li>" +
                                "<li>Sirva com a salada temperada com azeite.</li></ol>";
                    } else if (idade >= 31 && idade <= 50) {
                        planoRefeicao = "<h5>Café da Manhã: Pão com Ovo Cozido (450 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>2 fatias de pão integral</li><li>1 ovo cozido</li><li>1/2 xícara de suco natural</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Cozinhe o ovo por 8 minutos e descasque.</li>" +
                                "<li>Toste o pão integral.</li>" +
                                "<li>Sirva com o suco natural.</li></ol>" +
                                "<h5>Almoço: Quinoa com Frango (600 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>120 g de peito de frango</li><li>1/2 xícara de quinoa cozida</li><li>1 xícara de salada de rúcula</li><li>1 colher de sopa de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o frango com sal e pimenta.</li>" +
                                "<li>Grelhe o frango por 5-7 minutos.</li>" +
                                "<li>Cozinhe a quinoa conforme instruções.</li>" +
                                "<li>Sirva com a salada temperada com azeite.</li></ol>" +
                                "<h5>Lanche: Iogurte com Chia (200 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1 potes de iogurte desnatado (150 g)</li><li>1 colher de chá de chia</li><li>1/2 xícara de frutas</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Misture o iogurte com a chia e as frutas.</li>" +
                                "<li>Deixe descansar por 10 minutos.</li>" +
                                "<li>Sirva frio.</li></ol>" +
                                "<h5>Jantar: Peixe com Legumes (500 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de filé de peixe</li><li>1 xícara de legumes no vapor</li><li>1 batata-doce pequena</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o peixe com sal e limão.</li>" +
                                "<li>Asse o peixe a 180°C por 15 minutos.</li>" +
                                "<li>Cozinhe os legumes no vapor por 5 minutos.</li>" +
                                "<li>Asse a batata-doce a 200°C por 30 minutos.</li>" +
                                "<li>Sirva com azeite.</li></ol>";
                    } else {
                        planoRefeicao = "<h5>Café da Manhã: Mingau de Aveia (350 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1/3 xícara de aveia</li><li>1 xícara de leite desnatado</li><li>1/2 xícara de frutas frescas</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Aqueça o leite em uma panela.</li>" +
                                "<li>Adicione a aveia e cozinhe por 5 minutos.</li>" +
                                "<li>Misture as frutas e sirva.</li></ol>" +
                                "<h5>Almoço: Frango com Brócolis (450 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de peito de frango</li><li>1 xícara de brócolis cozido</li><li>1/2 xícara de arroz integral</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o frango com sal e pimenta.</li>" +
                                "<li>Grelhe o frango por 5-7 minutos.</li>" +
                                "<li>Cozinhe o brócolis no vapor por 5 minutos.</li>" +
                                "<li>Sirva com o arroz integral.</li></ol>" +
                                "<h5>Lanche: Frutas com Castanhas (150 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1/2 xícara de frutas frescas</li><li>10 g de castanhas</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Corte as frutas em pedaços.</li>" +
                                "<li>Misture com as castanhas e sirva.</li></ol>" +
                                "<h5>Jantar: Omelete com Legumes (350 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>2 ovos inteiros</li><li>1 xícara de espinafre picado</li><li>1 colher de chá de azeite</li><li>Sal e pimenta a gosto</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Bata os ovos e tempere com sal e pimenta.</li>" +
                                "<li>Aqueça o azeite em uma frigideira.</li>" +
                                "<li>Refogue o espinafre por 2 minutos.</li>" +
                                "<li>Despeje os ovos e cozinhe até firmar.</li>" +
                                "<li>Sirva com uma salada verde.</li></ol>";
                    }
                } else if (peso >= 91 && peso <= 110) {
                    if (idade >= 18 && idade <= 30) {
                        planoRefeicao = "<h5>Café da Manhã: Omelete com Abacate (500 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>2 ovos inteiros</li><li>1/4 de abacate</li><li>2 fatias de pão integral</li><li>1/2 xícara de suco de laranja</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Bata os ovos e cozinhe em uma frigideira antiaderente por 3-4 minutos.</li>" +
                                "<li>Toste o pão integral.</li>" +
                                "<li>Espalhe o abacate sobre o pão.</li>" +
                                "<li>Sirva com o suco de laranja.</li></ol>" +
                                "<h5>Almoço: Filé Mignon com Arroz (700 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>150 g de filé mignon</li><li>1 xícara de arroz integral cozido</li><li>1 xícara de salada verde</li><li>1 colher de sopa de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o filé com sal e pimenta.</li>" +
                                "<li>Grelhe o filé por 4-5 minutos de cada lado.</li>" +
                                "<li>Cozinhe o arroz integral conforme instruções.</li>" +
                                "<li>Sirva com a salada temperada com azeite.</li></ol>" +
                                "<h5>Lanche: Shake de Proteína (300 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1 scoop de proteína em pó</li><li>1 xícara de leite desnatado</li><li>1 banana</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Misture todos os ingredientes no liquidificador.</li>" +
                                "<li>Bata até ficar homogêneo.</li>" +
                                "<li>Sirva gelado.</li></ol>" +
                                "<h5>Jantar: Salmão com Batata-Doce (600 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>120 g de salmão</li><li>1 batata-doce média</li><li>1 xícara de legumes no vapor</li><li>1 colher de sopa de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o salmão com sal e limão.</li>" +
                                "<li>Grelhe o salmão por 4-5 minutos de cada lado.</li>" +
                                "<li>Asse a batata-doce a 200°C por 30 minutos.</li>" +
                                "<li>Sirva com os legumes temperados com azeite.</li></ol>";
                    } else if (idade >= 31 && idade <= 50) {
                        planoRefeicao = "<h5>Café da Manhã: Torrada com Ricota (400 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>2 fatias de pão integral</li><li>2 colheres de sopa de ricota</li><li>1/2 xícara de suco natural</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Toste o pão integral.</li>" +
                                "<li>Espalhe a ricota sobre as fatias.</li>" +
                                "<li>Sirva com o suco natural.</li></ol>" +
                                "<h5>Almoço: Frango com Quinoa (600 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>120 g de peito de frango</li><li>1/2 xícara de quinoa cozida</li><li>1 xícara de brócolis refogado</li><li>1 colher de sopa de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o frango com sal e pimenta.</li>" +
                                "<li>Grelhe o frango por 5-7 minutos.</li>" +
                                "<li>Cozinhe a quinoa conforme instruções.</li>" +
                                "<li>Refogue o brócolis com azeite por 4 minutos.</li>" +
                                "<li>Sirva tudo junto.</li></ol>" +
                                "<h5>Lanche: Iogurte com Granola (200 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1 potes de iogurte natural (150 g)</li><li>2 colheres de sopa de granola</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Misture o iogurte com a granola.</li>" +
                                "<li>Sirva imediatamente.</li></ol>" +
                                "<h5>Jantar: Peixe com Legumes (500 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de filé de peixe</li><li>1 xícara de legumes no vapor</li><li>1 batata-doce pequena</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o peixe com sal e limão.</li>" +
                                "<li>Asse o peixe a 180°C por 15 minutos.</li>" +
                                "<li>Cozinhe os legumes no vapor por 5 minutos.</li>" +
                                "<li>Asse a batata-doce a 200°C por 30 minutos.</li>" +
                                "<li>Sirva com azeite.</li></ol>";
                    } else {
                        planoRefeicao = "<h5>Café da Manhã: Aveia com Frutas (350 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1/3 xícara de aveia</li><li>1 xícara de leite desnatado</li><li>1/2 xícara de frutas frescas</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Aqueça o leite em uma panela.</li>" +
                                "<li>Adicione a aveia e cozinhe por 5 minutos.</li>" +
                                "<li>Misture as frutas e sirva.</li></ol>" +
                                "<h5>Almoço: Frango com Legumes (450 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de peito de frango</li><li>1 xícara de legumes refogados</li><li>1/2 xícara de arroz integral</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o frango com sal e pimenta.</li>" +
                                "<li>Grelhe o frango por 5-7 minutos.</li>" +
                                "<li>Refogue os legumes com azeite por 5 minutos.</li>" +
                                "<li>Sirva com o arroz integral.</li></ol>" +
                                "<h5>Lanche: Frutas com Nozes (150 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1/2 xícara de frutas frescas</li><li>10 g de nozes</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Corte as frutas em pedaços.</li>" +
                                "<li>Misture com as nozes e sirva.</li></ol>" +
                                "<h5>Jantar: Omelete com Espinafre (350 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>2 ovos inteiros</li><li>1 xícara de espinafre picado</li><li>1 colher de chá de azeite</li><li>Sal e pimenta a gosto</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Bata os ovos e tempere com sal e pimenta.</li>" +
                                "<li>Aqueça o azeite em uma frigideira.</li>" +
                                "<li>Refogue o espinafre por 2 minutos.</li>" +
                                "<li>Despeje os ovos e cozinhe até firmar.</li>" +
                                "<li>Sirva com uma salada verde.</li></ol>";
                    }
                } else if (peso >= 111 && peso <= 130) {
                    if (idade >= 18 && idade <= 30) {
                        planoRefeicao = "<h5>Café da Manhã: Smoothie de Frutas (500 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1 banana</li><li>1/2 xícara de aveia</li><li>1 xícara de leite desnatado</li><li>1 colher de sopa de pasta de amendoim</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Misture todos os ingredientes no liquidificador.</li>" +
                                "<li>Bata até ficar homogêneo.</li>" +
                                "<li>Sirva gelado.</li></ol>" +
                                "<h5>Almoço: Carne com Arroz (700 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>150 g de carne magra</li><li>1 xícara de arroz integral cozido</li><li>1 xícara de salada verde</li><li>1 colher de sopa de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere a carne com sal e pimenta.</li>" +
                                "<li>Grelhe a carne por 4-5 minutos de cada lado.</li>" +
                                "<li>Cozinhe o arroz integral conforme instruções.</li>" +
                                "<li>Sirva com a salada temperada com azeite.</li></ol>" +
                                "<h5>Lanche: Sanduíche de Frango (300 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1 fatia de pão integral</li><li>50 g de frango desfiado</li><li>1/4 de abacate</li><li>Folhas de alface</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Misture o frango desfiado com o abacate amassado.</li>" +
                                "<li>Espalhe sobre o pão integral.</li>" +
                                "<li>Adicione alface e sirva.</li></ol>" +
                                "<h5>Jantar: Salmão com Quinoa (600 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>120 g de salmão</li><li>1/2 xícara de quinoa cozida</li><li>1 xícara de legumes no vapor</li><li>1 colher de sopa de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o salmão com sal e limão.</li>" +
                                "<li>Grelhe o salmão por 4-5 minutos de cada lado.</li>" +
                                "<li>Cozinhe a quinoa conforme instruções.</li>" +
                                "<li>Sirva com os legumes temperados com azeite.</li></ol>";
                    } else if (idade >= 31 && idade <= 50) {
                        planoRefeicao = "<h5>Café da Manhã: Pão com Ovo (400 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>2 fatias de pão integral</li><li>1 ovo cozido</li><li>1/2 xícara de suco natural</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Cozinhe o ovo por 8 minutos e descasque.</li>" +
                                "<li>Toste o pão integral.</li>" +
                                "<li>Sirva com o suco natural.</li></ol>" +
                                "<h5>Almoço: Frango com Quinoa (600 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>120 g de peito de frango</li><li>1/2 xícara de quinoa cozida</li><li>1 xícara de brócolis refogado</li><li>1 colher de sopa de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o frango com sal e pimenta.</li>" +
                                "<li>Grelhe o frango por 5-7 minutos.</li>" +
                                "<li>Cozinhe a quinoa conforme instruções.</li>" +
                                "<li>Refogue o brócolis com azeite por 4 minutos.</li>" +
                                "<li>Sirva tudo junto.</li></ol>" +
                                "<h5>Lanche: Iogurte com Chia (200 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1 potes de iogurte desnatado (150 g)</li><li>1 colher de chá de chia</li><li>1/2 xícara de frutas</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Misture o iogurte com a chia e as frutas.</li>" +
                                "<li>Deixe descansar por 10 minutos.</li>" +
                                "<li>Sirva frio.</li></ol>" +
                                "<h5>Jantar: Peixe com Batata-Doce (500 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de filé de peixe</li><li>1 batata-doce pequena</li><li>1 xícara de legumes no vapor</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o peixe com sal e limão.</li>" +
                                "<li>Asse o peixe a 180°C por 15 minutos.</li>" +
                                "<li>Asse a batata-doce a 200°C por 30 minutos.</li>" +
                                "<li>Sirva com os legumes temperados com azeite.</li></ol>";
                    } else {
                        planoRefeicao = "<h5>Café da Manhã: Aveia com Frutas (350 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1/3 xícara de aveia</li><li>1 xícara de leite desnatado</li><li>1/2 xícara de frutas frescas</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Aqueça o leite em uma panela.</li>" +
                                "<li>Adicione a aveia e cozinhe por 5 minutos.</li>" +
                                "<li>Misture as frutas e sirva.</li></ol>" +
                                "<h5>Almoço: Frango com Legumes (450 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de peito de frango</li><li>1 xícara de legumes refogados</li><li>1/2 xícara de arroz integral</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o frango com sal e pimenta.</li>" +
                                "<li>Grelhe o frango por 5-7 minutos.</li>" +
                                "<li>Refogue os legumes com azeite por 5 minutos.</li>" +
                                "<li>Sirva com o arroz integral.</li></ol>" +
                                "<h5>Lanche: Frutas com Nozes (150 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1/2 xícara de frutas frescas</li><li>10 g de nozes</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Corte as frutas em pedaços.</li>" +
                                "<li>Misture com as nozes e sirva.</li></ol>" +
                                "<h5>Jantar: Omelete com Espinafre (350 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>2 ovos inteiros</li><li>1 xícara de espinafre picado</li><li>1 colher de chá de azeite</li><li>Sal e pimenta a gosto</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Bata os ovos e tempere com sal e pimenta.</li>" +
                                "<li>Aqueça o azeite em uma frigideira.</li>" +
                                "<li>Refogue o espinafre por 2 minutos.</li>" +
                                "<li>Despeje os ovos e cozinhe até firmar.</li>" +
                                "<li>Sirva com uma salada verde.</li></ol>";
                    }
                } else if (peso >= 131) {
                    if (idade >= 18 && idade <= 30) {
                        planoRefeicao = "<h5>Café da Manhã: Torrada com Abacate (450 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>2 fatias de pão integral</li><li>1/4 de abacate</li><li>1 ovo cozido</li><li>Sal a gosto</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Cozinhe o ovo por 8 minutos e descasque.</li>" +
                                "<li>Toste o pão integral.</li>" +
                                "<li>Espalhe o abacate sobre o pão e tempere com sal.</li>" +
                                "<li>Sirva com o ovo cozido.</li></ol>" +
                                "<h5>Almoço: Frango com Salada (600 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>120 g de peito de frango</li><li>1 xícara de salada verde</li><li>1/2 xícara de quinoa cozida</li><li>1 colher de sopa de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o frango com sal e pimenta.</li>" +
                                "<li>Grelhe o frango por 5-7 minutos.</li>" +
                                "<li>Cozinhe a quinoa conforme instruções.</li>" +
                                "<li>Sirva com a salada temperada com azeite.</li></ol>" +
                                "<h5>Lanche: Iogurte com Chia (200 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1 potes de iogurte desnatado (150 g)</li><li>1 colher de chá de chia</li><li>1/2 xícara de frutas</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Misture o iogurte com a chia e as frutas.</li>" +
                                "<li>Deixe descansar por 10 minutos.</li>" +
                                "<li>Sirva frio.</li></ol>" +
                                "<h5>Jantar: Peixe com Legumes (500 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de filé de peixe</li><li>1 xícara de legumes no vapor</li><li>1 colher de chá de azeite</li><li>Sal e limão a gosto</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o peixe com sal e limão.</li>" +
                                "<li>Grelhe o peixe por 4-5 minutos de cada lado.</li>" +
                                "<li>Cozinhe os legumes no vapor por 5 minutos.</li>" +
                                "<li>Sirva com azeite.</li></ol>";
                    } else if (idade >= 31 && idade <= 50) {
                        planoRefeicao = "<h5>Café da Manhã: Aveia com Frutas (350 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1/3 xícara de aveia</li><li>1 xícara de leite desnatado</li><li>1/2 xícara de frutas frescas</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Aqueça o leite em uma panela.</li>" +
                                "<li>Adicione a aveia e cozinhe por 5 minutos.</li>" +
                                "<li>Misture as frutas e sirva.</li></ol>" +
                                "<h5>Almoço: Frango com Brócolis (500 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de peito de frango</li><li>1 xícara de brócolis cozido</li><li>1/2 xícara de arroz integral</li><li>1 colher de chá de azeite</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o frango com sal e pimenta.</li>" +
                                "<li>Grelhe o frango por 5-7 minutos.</li>" +
                                "<li>Cozinhe o brócolis no vapor por 5 minutos.</li>" +
                                "<li>Sirva com o arroz integral.</li></ol>" +
                                "<h5>Lanche: Frutas com Nozes (150 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1/2 xícara de frutas frescas</li><li>10 g de nozes</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Corte as frutas em pedaços.</li>" +
                                "<li>Misture com as nozes e sirva.</li></ol>" +
                                "<h5>Jantar: Omelete com Espinafre (400 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>2 ovos inteiros</li><li>1 xícara de espinafre picado</li><li>1 colher de chá de azeite</li><li>Sal e pimenta a gosto</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Bata os ovos e tempere com sal e pimenta.</li>" +
                                "<li>Aqueça o azeite em uma frigideira.</li>" +
                                "<li>Refogue o espinafre por 2 minutos.</li>" +
                                "<li>Despeje os ovos e cozinhe até firmar.</li>" +
                                "<li>Sirva com uma salada verde.</li></ol>";
                    } else {
                        planoRefeicao = "<h5>Café da Manhã: Mingau de Aveia (300 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1/3 xícara de aveia</li><li>1 xícara de leite desnatado</li><li>1 maçã fatiada</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Aqueça o leite em uma panela.</li>" +
                                "<li>Adicione a aveia e cozinhe por 5 minutos.</li>" +
                                "<li>Misture a maçã fatiada e sirva.</li></ol>" +
                                "<h5>Almoço: Peixe com Legumes (400 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de filé de peixe</li><li>1 xícara de legumes no vapor</li><li>1 colher de chá de azeite</li><li>Sal e limão a gosto</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Tempere o peixe com sal e limão.</li>" +
                                "<li>Grelhe o peixe por 4-5 minutos de cada lado.</li>" +
                                "<li>Cozinhe os legumes no vapor por 5 minutos.</li>" +
                                "<li>Sirva com azeite.</li></ol>" +
                                "<h5>Lanche: Frutas com Castanhas (150 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>1/2 xícara de frutas frescas</li><li>10 g de castanhas</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Corte as frutas em pedaços.</li>" +
                                "<li>Misture com as castanhas e sirva.</li></ol>" +
                                "<h5>Jantar: Salada com Frango (350 kcal)</h5>" +
                                "<strong>Ingredientes:</strong><ul>" +
                                "<li>100 g de frango desfiado</li><li>1 xícara de salada de folhas verdes</li><li>1 colher de chá de azeite</li><li>Sal e limão a gosto</li></ul>" +
                                "<strong>Modo de Preparo:</strong><ol>" +
                                "<li>Cozinhe o frango e desfie.</li>" +
                                "<li>Misture com a salada de folhas verdes.</li>" +
                                "<li>Tempere com azeite, sal e limão.</li>" +
                                "<li>Sirva fresco.</li></ol>";
                    }
                } else {
                    planoRefeicao = "Nenhum plano de refeição disponível para o peso informado. Consulte um nutricionista.";
                }
                model.addAttribute("planoRefeicao", planoRefeicao);
                logger.info("Plano de refeição gerado para usuário: {}, peso: {}, idade: {}", email, peso, idade);
            } else {
                logger.warn("Usuário não encontrado: {}", email);
            }
        } else {
            logger.warn("Nenhum usuário autenticado encontrado");
        }
        return "planorefeicoes";
    }

    @GetMapping("/RastreamentoAlimentos")
    public String rastreamentoAlimento() {
        logger.info("Acessando a página de rastreamento de alimentos");
        return "rastreamentoalimentos";
    }

    @GetMapping("/ReceitaSaudaveis")
    public String receitassaudaveis() {
        logger.info("Acessando a página de receitas saudáveis");
        return "receitasaudaveis";
    }
}