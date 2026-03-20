import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class DarkRPG {

    // ==========================================
    // 1. MOTOR DEL PATRON DECORATOR (Logica en Ingles)
    // ==========================================

    public interface Fighter {
        String getDescription();
        int getAttackPower();
        int getDefense();
        int getMaxHealth();
    }

    // --- ENTIDADES BASE ---
    public static class BaseTarnished implements Fighter {
        @Override
        public String getDescription() { return "Tarnished (Base)"; }
        @Override
        public int getAttackPower() { return 25; }
        @Override
        public int getDefense() { return 15; }
        @Override
        public int getMaxHealth() { return 800; }
    }

    public static class AbyssalInvader implements Fighter {
        @Override
        public String getDescription() { return "Abyssal Invader"; }
        @Override
        public int getAttackPower() { return 35; }
        @Override
        public int getDefense() { return 20; }
        @Override
        public int getMaxHealth() { return 1200; }
    }

    // --- ABSTRACCION DEL DECORADOR ---
    public static abstract class EquipmentDecorator implements Fighter {
        protected Fighter fighter;

        public EquipmentDecorator(Fighter fighter) {
            this.fighter = fighter;
        }

        @Override
        public String getDescription() { return fighter.getDescription(); }
        @Override
        public int getAttackPower() { return fighter.getAttackPower(); }
        @Override
        public int getDefense() { return fighter.getDefense(); }
        @Override
        public int getMaxHealth() { return fighter.getMaxHealth(); }
    }

    // --- DECORADORES DE ARMAS (Jugador y Enemigo) ---
    public static class RiversOfBloodKatana extends EquipmentDecorator {
        public RiversOfBloodKatana(Fighter f) { super(f); }
        @Override
        public int getAttackPower() { return fighter.getAttackPower() + 185; }
    }

    public static class MoonveilKatana extends EquipmentDecorator {
        public MoonveilKatana(Fighter f) { super(f); }
        @Override
        public int getAttackPower() { return fighter.getAttackPower() + 165; }
    }

    public static class GraftedBladeGreatsword extends EquipmentDecorator {
        public GraftedBladeGreatsword(Fighter f) { super(f); }
        @Override
        public int getAttackPower() { return fighter.getAttackPower() + 210; }
    }

    public static class BlasphemousGreatsword extends EquipmentDecorator {
        public BlasphemousGreatsword(Fighter f) { super(f); }
        @Override
        public int getAttackPower() { return fighter.getAttackPower() + 190; }
    }

    public static class MalikethsBlackBlade extends EquipmentDecorator {
        public MalikethsBlackBlade(Fighter f) { super(f); }
        @Override
        public int getAttackPower() { return fighter.getAttackPower() + 230; }
    }

    // --- DECORADORES DE ARMADURAS ---
    public static class RadahnArmorSet extends EquipmentDecorator {
        public RadahnArmorSet(Fighter f) { super(f); }
        @Override
        public int getDefense() { return fighter.getDefense() + 145; }
    }

    public static class CrucibleKnightSet extends EquipmentDecorator {
        public CrucibleKnightSet(Fighter f) { super(f); }
        @Override
        public int getDefense() { return fighter.getDefense() + 130; }
    }

    public static class RagingWolfSet extends EquipmentDecorator {
        public RagingWolfSet(Fighter f) { super(f); }
        @Override
        public int getDefense() { return fighter.getDefense() + 95; }
    }

    public static class MalikethsArmorSet extends EquipmentDecorator {
        public MalikethsArmorSet(Fighter f) { super(f); }
        @Override
        public int getDefense() { return fighter.getDefense() + 125; }
    }

    public static class TreeCrucibleSet extends EquipmentDecorator {
        public TreeCrucibleSet(Fighter f) { super(f); }
        @Override
        public int getDefense() { return fighter.getDefense() + 135; }
    }

    // --- DECORADORES DE TALISMANES ---
    public static class ErdtreeFavorTalisman extends EquipmentDecorator {
        public ErdtreeFavorTalisman(Fighter f) { super(f); }
        @Override
        public int getMaxHealth() { return fighter.getMaxHealth() + 350; }
        @Override
        public int getDefense() { return fighter.getDefense() + 25; }
    }

    public static class RadagonsSoreseal extends EquipmentDecorator {
        public RadagonsSoreseal(Fighter f) { super(f); }
        @Override
        public int getAttackPower() { return fighter.getAttackPower() + 80; }
        @Override
        public int getMaxHealth() { return fighter.getMaxHealth() + 250; }
        @Override
        public int getDefense() { return fighter.getDefense() - 40; }
    }

    public static class LordOfBloodExultation extends EquipmentDecorator {
        public LordOfBloodExultation(Fighter f) { super(f); }
        @Override
        public int getAttackPower() { return fighter.getAttackPower() + 90; }
    }

    // ==========================================
    // 2. SERVIDOR HTTP EMBEBIDO Y FRONTEND AVANZADO
    // ==========================================

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new FrontendHandler());
        server.createContext("/api/resolve", new CombatEngineHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Motor de simulacion estructural inicializado en puerto 8080.");
    }

    static class FrontendHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String html = "<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'><title>Simulador Avanzado - Decorator</title>" +
                    "<style>" +
                    ":root { --bg-dark: #07090c; --panel-bg: #11151c; --border-color: #2a3441; --text-main: #c9d1d9; --text-muted: #8b949e; --accent-gold: #c5a059; --accent-red: #8a1c1c; --accent-blue: #1c528a; }" +
                    "body { font-family: 'Segoe UI', system-ui, sans-serif; background-color: var(--bg-dark); color: var(--text-main); margin: 0; padding: 20px; text-align: center; overflow-x: hidden; }" +
                    "h1 { font-weight: 300; letter-spacing: 4px; text-transform: uppercase; border-bottom: 1px solid var(--border-color); padding-bottom: 15px; margin-bottom: 5px; color: #fff; }" +
                    ".academic-subtitle { color: var(--text-muted); font-size: 14px; letter-spacing: 2px; text-transform: uppercase; margin-bottom: 30px; }" +

                    ".dashboard { display: grid; grid-template-columns: 1fr 1fr; gap: 30px; max-width: 1400px; margin: 0 auto; }" +
                    ".panel { background: var(--panel-bg); border: 1px solid var(--border-color); border-radius: 6px; padding: 25px; display: flex; flex-direction: column; position: relative; box-shadow: 0 10px 30px rgba(0,0,0,0.5); }" +
                    ".panel-title { font-size: 20px; font-weight: 600; text-transform: uppercase; color: var(--accent-gold); margin-top: 0; margin-bottom: 20px; text-align: left; letter-spacing: 1px; border-bottom: 1px solid #222; padding-bottom: 10px; }" +

                    ".inventory-grid { display: grid; grid-template-columns: repeat(2, 1fr); gap: 12px; margin-bottom: 25px; text-align: left; }" +
                    ".inv-slot { background: #161b22; border: 1px solid #30363d; border-radius: 6px; padding: 12px; cursor: pointer; transition: all 0.2s; position: relative; overflow: hidden; }" +
                    ".inv-slot:hover { border-color: var(--text-muted); background: #1c232d; transform: translateY(-2px); }" +
                    "input[type='radio'], input[type='checkbox'] { position: absolute; opacity: 0; cursor: pointer; height: 0; width: 0; }" +
                    ".slot-content { display: flex; flex-direction: column; pointer-events: none; }" +
                    ".slot-title { font-weight: bold; font-size: 14px; margin-bottom: 5px; color: #fff; }" +
                    ".slot-stats { font-size: 12px; color: var(--text-muted); }" +
                    ".stat-buff { color: #3fb950; } .stat-nerf { color: #f85149; }" +

                    "input:checked ~ .slot-content .slot-title { color: var(--accent-gold); }" +
                    "input:checked ~ .slot-content { filter: drop-shadow(0 0 5px rgba(197, 160, 89, 0.4)); }" +
                    ".inv-slot.active-slot { border-color: var(--accent-gold); background: #1f242b; box-shadow: inset 0 0 10px rgba(197, 160, 89, 0.1); }" +

                    ".render-viewport { height: 380px; background: radial-gradient(circle at center, #1c232d 0%, var(--bg-dark) 80%); border: 1px solid var(--border-color); border-radius: 6px; display: flex; justify-content: center; align-items: flex-end; padding-bottom: 30px; position: relative; margin-bottom: 25px; overflow: hidden; }" +
                    ".floor { position: absolute; bottom: 0; width: 100%; height: 60px; background: linear-gradient(to top, #0a0c10, transparent); }" +

                    // Anatomia CSS
                    ".skeleton { position: relative; width: 130px; height: 280px; display: flex; flex-direction: column; align-items: center; z-index: 10; transform-origin: center bottom; transition: all 0.3s; }" +
                    ".head-bone { width: 40px; height: 50px; border-radius: 40% 40% 50% 50%; background: #a8b2bd; position: relative; z-index: 4; border: 2px solid #000; transition: all 0.4s; }" +
                    ".torso-bone { width: 70px; height: 100px; border-radius: 12px; background: #6e7681; position: relative; z-index: 3; margin-top: -4px; border: 2px solid #000; transition: all 0.4s; }" +
                    ".arm-bone { width: 20px; height: 100px; border-radius: 10px; background: #6e7681; position: absolute; top: 50px; z-index: 2; border: 2px solid #000; transform-origin: 10px 10px; transition: all 0.4s; }" +
                    ".arm-bone.left { left: 10px; transform: rotate(15deg); }" +
                    ".arm-bone.right { right: 10px; transform: rotate(-15deg); }" +
                    ".leg-bone { width: 24px; height: 120px; border-radius: 8px; background: #484f58; position: absolute; top: 140px; z-index: 1; border: 2px solid #000; transition: all 0.4s; }" +
                    ".leg-bone.left { left: 30px; }" +
                    ".leg-bone.right { right: 30px; }" +

                    ".skeleton.stance-player { animation: breathe 3.5s infinite ease-in-out; }" +
                    ".skeleton.stance-enemy { animation: breathe 4s infinite ease-in-out reverse; }" +
                    ".skeleton.stance-enemy .head-bone { background: #3d1c1c; border-color: #5c0000; }" +
                    ".skeleton.stance-enemy .torso-bone, .skeleton.stance-enemy .arm-bone { background: #2d1414; border-color: #5c0000; }" +
                    ".skeleton.stance-enemy .leg-bone { background: #1a0b0b; border-color: #5c0000; }" +

                    // Armaduras Jugador
                    "body:has(#p_arm_radahn:checked) .stance-player .torso-bone { background: #8b6d31; border-color: #ffd700; width: 95px; border-radius: 40px 40px 15px 15px; }" +
                    "body:has(#p_arm_radahn:checked) .stance-player .head-bone { background: #8b6d31; border-color: #ffd700; border-radius: 12px; }" +
                    "body:has(#p_arm_radahn:checked) .stance-player .head-bone::after { content: ''; position: absolute; top: -40px; left: 14px; width: 8px; height: 50px; background: #d12a2a; border-radius: 4px; box-shadow: 0 0 15px #d12a2a; }" +
                    "body:has(#p_arm_radahn:checked) .stance-player .arm-bone { background: #8b6d31; border-color: #ffd700; width: 28px; }" +
                    "body:has(#p_arm_radahn:checked) .stance-player .leg-bone { background: #5a461e; border-color: #ffd700; width: 32px; }" +

                    "body:has(#p_arm_crucible:checked) .stance-player .torso-bone { background: #a67c52; border-color: #e6b88a; width: 80px; border-radius: 10px; clip-path: polygon(10% 0, 90% 0, 100% 100%, 0 100%); }" +
                    "body:has(#p_arm_crucible:checked) .stance-player .head-bone { background: #a67c52; border-color: #e6b88a; border-radius: 25px 25px 5px 5px; }" +
                    "body:has(#p_arm_crucible:checked) .stance-player .arm-bone { background: #a67c52; border-color: #e6b88a; }" +
                    "body:has(#p_arm_crucible:checked) .stance-player .leg-bone { background: #7a5c3d; border-color: #e6b88a; }" +

                    "body:has(#p_arm_wolf:checked) .stance-player .torso-bone { background: #555; border-color: #aaa; width: 75px; box-shadow: inset 0 0 10px #222; }" +
                    "body:has(#p_arm_wolf:checked) .stance-player .head-bone { background: #444; border-color: #aaa; border-radius: 5px; }" +
                    "body:has(#p_arm_wolf:checked) .stance-player .arm-bone, body:has(#p_arm_wolf:checked) .stance-player .leg-bone { background: #555; border-color: #aaa; }" +

                    // Armaduras Enemigo
                    "body:has(#e_arm_tree:checked) .stance-enemy .torso-bone { background: #826343; border-color: #e6b88a; width: 85px; border-radius: 10px; }" +
                    "body:has(#e_arm_tree:checked) .stance-enemy .head-bone { background: #826343; border-color: #e6b88a; border-radius: 5px 5px 20px 20px; }" +
                    "body:has(#e_arm_tree:checked) .stance-enemy .arm-bone, body:has(#e_arm_tree:checked) .stance-enemy .leg-bone { background: #826343; border-color: #e6b88a; }" +

                    "body:has(#e_arm_maliketh:checked) .stance-enemy .torso-bone { background: #111; border-color: #c5a059; width: 80px; box-shadow: inset 0 0 20px #c5a059; }" +
                    "body:has(#e_arm_maliketh:checked) .stance-enemy .head-bone { background: #111; border-color: #c5a059; border-radius: 5px; }" +
                    "body:has(#e_arm_maliketh:checked) .stance-enemy .arm-bone, body:has(#e_arm_maliketh:checked) .stance-enemy .leg-bone { background: #111; border-color: #c5a059; }" +

                    ".weapon-node { position: absolute; bottom: -30px; transform-origin: center 85%; display: none; z-index: 6; }" +

                    // Armas Jugador
                    "body:has(#p_wep_rivers:checked) .stance-player .arm-bone.right .weapon-node.rivers-blade { display: block; }" +
                    ".rivers-blade { width: 8px; height: 210px; background: linear-gradient(to top, #222 5%, #aa0000 30%, #ff1a1a 80%, #fff 100%); left: 4px; transform: rotate(12deg); border-radius: 4px; box-shadow: 0 0 20px #ff0000; }" +

                    "body:has(#p_wep_moonveil:checked) .stance-player .arm-bone.right .weapon-node.moonveil-blade { display: block; }" +
                    ".moonveil-blade { width: 8px; height: 200px; background: linear-gradient(to top, #111 10%, #0055aa 40%, #00d4ff 100%); left: 4px; transform: rotate(15deg); border-radius: 4px; box-shadow: 0 0 20px #00aaff; }" +

                    "body:has(#p_wep_grafted:checked) .stance-player .arm-bone.right .weapon-node.grafted-blade { display: block; }" +
                    ".grafted-blade { width: 30px; height: 220px; background: #4a4e52; border: 3px solid #222; left: -8px; transform: rotate(5deg); border-radius: 2px; box-shadow: inset 0 0 15px #111; }" +
                    ".grafted-blade::after { content: ''; position: absolute; width: 50px; height: 15px; background: #333; top: 160px; left: -10px; border: 2px solid #111; }" +

                    // Armas Enemigo
                    "body:has(#e_wep_blasphemous:checked) .stance-enemy .arm-bone.left .weapon-node.blasphemous-blade { display: block; }" +
                    ".blasphemous-blade { width: 20px; height: 230px; background: #5c1e10; border: 2px solid #ff4500; right: -2px; transform: rotate(-22deg); border-radius: 2px; box-shadow: inset 0 0 15px #ff0000, 0 0 20px rgba(255, 69, 0, 0.5); }" +
                    ".blasphemous-blade::before { content: ''; position: absolute; width: 50px; height: 12px; background: #8a2b0e; top: 180px; left: -15px; border: 2px solid #333; }" +

                    "body:has(#e_wep_maliketh:checked) .stance-enemy .arm-bone.left .weapon-node.maliketh-blade { display: block; }" +
                    ".maliketh-blade { width: 28px; height: 240px; background: #111; border: 2px solid #8a1c1c; right: -5px; transform: rotate(-18deg); border-radius: 5px; box-shadow: 0 0 25px rgba(138, 28, 28, 0.8); }" +

                    ".stats-panel { margin-top: auto; padding-top: 25px; border-top: 1px solid var(--border-color); text-align: left; }" +
                    ".stat-row { display: flex; align-items: center; margin-bottom: 15px; font-size: 14px; font-weight: 600; }" +
                    ".stat-label { width: 75px; color: var(--text-muted); text-transform: uppercase; letter-spacing: 1px; }" +
                    ".stat-bar-bg { flex-grow: 1; height: 10px; background: #0b0e12; border-radius: 5px; overflow: hidden; margin: 0 15px; border: 1px solid #222; position: relative; }" +
                    ".stat-bar-fill { height: 100%; width: 0%; transition: width 0.6s cubic-bezier(0.4, 0, 0.2, 1); }" +
                    ".fill-hp { background: linear-gradient(90deg, #6b1414, var(--accent-red)); }" +
                    ".fill-atk { background: linear-gradient(90deg, #8f7238, var(--accent-gold)); }" +
                    ".fill-def { background: linear-gradient(90deg, #123456, var(--accent-blue)); }" +
                    ".stat-value { width: 55px; text-align: right; font-family: monospace; font-size: 16px; color: #fff; }" +

                    ".control-center { grid-column: 1 / -1; display: flex; justify-content: center; padding: 40px 0 20px 0; }" +
                    ".btn-execute { background: transparent; color: var(--accent-gold); border: 2px solid var(--accent-gold); padding: 18px 70px; font-size: 18px; font-weight: bold; letter-spacing: 3px; text-transform: uppercase; cursor: pointer; transition: all 0.3s; border-radius: 4px; box-shadow: 0 0 15px rgba(197, 160, 89, 0.1); }" +
                    ".btn-execute:hover { background: var(--accent-gold); color: var(--bg-dark); box-shadow: 0 0 25px rgba(197, 160, 89, 0.5); transform: scale(1.02); }" +
                    ".btn-execute:disabled { opacity: 0.4; cursor: not-allowed; transform: scale(1); border-color: #555; color: #555; }" +

                    "@keyframes breathe { 0%, 100% { transform: translateY(0); } 50% { transform: translateY(-6px); } }" +
                    "@keyframes screenShake { 0%, 100% { transform: translate(0, 0) rotate(0deg); } 25% { transform: translate(-6px, 6px) rotate(-1deg); } 50% { transform: translate(6px, -6px) rotate(1deg); } 75% { transform: translate(-6px, -6px) rotate(0deg); } }" +
                    "@keyframes flashImpact { 0%, 100% { filter: brightness(1); } 50% { filter: brightness(2) sepia(1) hue-rotate(-50deg) saturate(5); } }" +
                    "@keyframes clashLeft { 0%, 100% { transform: translateX(0) rotate(0); } 50% { transform: translateX(300px) translateY(-20px) rotate(25deg); z-index: 50; } }" +
                    "@keyframes clashRight { 0%, 100% { transform: translateX(0) rotate(0); } 50% { transform: translateX(-300px) translateY(-20px) rotate(-25deg); z-index: 50; } }" +
                    "@keyframes staggerHit { 0%, 100% { transform: translateX(0) rotate(0); } 20% { transform: translateX(20px) rotate(10deg); filter: brightness(3) drop-shadow(0 0 30px red); } 40% { transform: translateX(-15px); } 60% { transform: translateX(10px); } }" +

                    "body.combat-active { animation: screenShake 0.5s ease-in-out 0.4s; }" +
                    "body.combat-active .render-viewport { animation: flashImpact 0.5s ease-in-out 0.4s; }" +
                    ".stance-player.attacking { animation: clashLeft 0.9s cubic-bezier(0.25, 0.46, 0.45, 0.94); }" +
                    ".stance-enemy.attacking { animation: clashRight 0.9s cubic-bezier(0.25, 0.46, 0.45, 0.94); }" +
                    ".skeleton.staggered { animation: staggerHit 0.6s ease-out 0.4s forwards; }" +
                    ".hp-depleted { width: 0% !important; transition: width 1.2s ease-out 0.6s !important; }" +

                    ".overlay-results { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(5,7,10,0.9); display: flex; justify-content: center; align-items: center; z-index: 100; opacity: 0; pointer-events: none; transition: opacity 0.8s; backdrop-filter: blur(8px); }" +
                    ".overlay-results.show { opacity: 1; pointer-events: all; }" +
                    ".results-card { background: #0d1117; border: 2px solid var(--accent-gold); padding: 40px; border-radius: 8px; text-align: center; max-width: 750px; width: 90%; transform: translateY(50px); transition: transform 0.6s cubic-bezier(0.175, 0.885, 0.32, 1.275); box-shadow: 0 30px 60px rgba(0,0,0,0.8); }" +
                    ".overlay-results.show .results-card { transform: translateY(0); }" +
                    ".res-title { font-size: 38px; color: var(--accent-gold); text-transform: uppercase; margin-top: 0; letter-spacing: 3px; font-weight: 800; text-shadow: 0 0 20px rgba(197, 160, 89, 0.5); }" +
                    ".res-log { font-size: 16px; color: var(--text-main); margin: 30px 0; line-height: 1.8; font-family: 'Courier New', monospace; background: #050608; padding: 25px; border: 1px solid #222; text-align: left; border-radius: 4px; }" +
                    ".log-highlight { color: var(--accent-gold); font-weight: bold; }" +

                    "</style></head><body>" +
                    "<h1>Motor de Estructuras Decorator</h1>" +
                    "<div class='academic-subtitle'>Proyecto de Patrones de Software - Semestre IV</div>" +

                    "<div class='dashboard'>" +
                    // PANEL JUGADOR
                    "<div class='panel'>" +
                    "<h2 class='panel-title'>Protagonista (Tarnished)</h2>" +

                    "<div class='render-viewport'>" +
                    "<div class='floor'></div>" +
                    "<div class='skeleton stance-player' id='pSkeleton'>" +
                    "<div class='head-bone'></div>" +
                    "<div class='torso-bone'></div>" +
                    "<div class='arm-bone left'></div>" +
                    "<div class='arm-bone right'>" +
                    "<div class='weapon-node rivers-blade'></div>" +
                    "<div class='weapon-node moonveil-blade'></div>" +
                    "<div class='weapon-node grafted-blade'></div>" +
                    "</div>" +
                    "<div class='leg-bone left'></div>" +
                    "<div class='leg-bone right'></div>" +
                    "</div>" +
                    "</div>" +

                    "<div class='inventory-grid'>" +
                    "<label class='inv-slot'><input type='radio' name='p_wep' id='p_wep_rivers' value='rivers' checked onchange='updateUIStyles()'><div class='slot-content'><span class='slot-title'>Ríos de Sangre</span><span class='slot-stats'><span class='stat-buff'>+185 Atq</span> (Fuego/Sangrado)</span></div></label>" +
                    "<label class='inv-slot'><input type='radio' name='p_wep' id='p_wep_moonveil' value='moonveil' onchange='updateUIStyles()'><div class='slot-content'><span class='slot-title'>Velo Lunar</span><span class='slot-stats'><span class='stat-buff'>+165 Atq</span> (Magia)</span></div></label>" +
                    "<label class='inv-slot' style='grid-column: 1 / -1;'><input type='radio' name='p_wep' id='p_wep_grafted' value='grafted' onchange='updateUIStyles()'><div class='slot-content'><span class='slot-title'>Espadón de la Hoja Injertada</span><span class='slot-stats'><span class='stat-buff'>+210 Atq</span> (Físico Pesado)</span></div></label>" +

                    "<label class='inv-slot'><input type='radio' name='p_arm' id='p_arm_radahn' value='radahn' checked onchange='updateUIStyles()'><div class='slot-content'><span class='slot-title'>Set de Radahn</span><span class='slot-stats'><span class='stat-buff'>+145 Def</span></span></div></label>" +
                    "<label class='inv-slot'><input type='radio' name='p_arm' id='p_arm_crucible' value='crucible' onchange='updateUIStyles()'><div class='slot-content'><span class='slot-title'>Set de Crisol</span><span class='slot-stats'><span class='stat-buff'>+130 Def</span></span></div></label>" +
                    "<label class='inv-slot' style='grid-column: 1 / -1;'><input type='radio' name='p_arm' id='p_arm_wolf' value='wolf' onchange='updateUIStyles()'><div class='slot-content'><span class='slot-title'>Lobo Sangriento</span><span class='slot-stats'><span class='stat-buff'>+95 Def</span> (Ligero)</span></div></label>" +

                    "<label class='inv-slot'><input type='checkbox' id='p_tal_favor' onchange='updateUIStyles()'><div class='slot-content'><span class='slot-title'>Favor del Árbol Áureo</span><span class='slot-stats'><span class='stat-buff'>+350 PS | +25 Def</span></span></div></label>" +
                    "<label class='inv-slot'><input type='checkbox' id='p_tal_radagon' onchange='updateUIStyles()'><div class='slot-content'><span class='slot-title'>Sello de Radagon</span><span class='slot-stats'><span class='stat-buff'>+80 Atq | +250 PS</span> | <span class='stat-nerf'>-40 Def</span></span></div></label>" +
                    "</div>" +

                    "<div class='stats-panel'>" +
                    "<div class='stat-row'><span class='stat-label'>PS MAX</span><div class='stat-bar-bg'><div class='stat-bar-fill fill-hp' id='p_hp_bar'></div></div><span class='stat-value' id='p_hp_val'>0</span></div>" +
                    "<div class='stat-row'><span class='stat-label'>ATAQUE</span><div class='stat-bar-bg'><div class='stat-bar-fill fill-atk' id='p_atk_bar'></div></div><span class='stat-value' id='p_atk_val'>0</span></div>" +
                    "<div class='stat-row'><span class='stat-label'>DEFENSA</span><div class='stat-bar-bg'><div class='stat-bar-fill fill-def' id='p_def_bar'></div></div><span class='stat-value' id='p_def_val'>0</span></div>" +
                    "</div>" +
                    "</div>" +

                    // PANEL ENEMIGO
                    "<div class='panel'>" +
                    "<h2 class='panel-title' style='color: var(--accent-red);'>Abyssal Invader</h2>" +

                    "<div class='render-viewport'>" +
                    "<div class='floor'></div>" +
                    "<div class='skeleton stance-enemy' id='eSkeleton'>" +
                    "<div class='head-bone'></div>" +
                    "<div class='torso-bone'></div>" +
                    "<div class='arm-bone left'>" +
                    "<div class='weapon-node blasphemous-blade'></div>" +
                    "<div class='weapon-node maliketh-blade'></div>" +
                    "</div>" +
                    "<div class='arm-bone right'></div>" +
                    "<div class='leg-bone left'></div>" +
                    "<div class='leg-bone right'></div>" +
                    "</div>" +
                    "</div>" +

                    "<div class='inventory-grid'>" +
                    "<label class='inv-slot'><input type='radio' name='e_wep' id='e_wep_blasphemous' value='blasphemous' checked onchange='updateUIStyles()'><div class='slot-content'><span class='slot-title'>Espada Blasfema</span><span class='slot-stats'><span class='stat-buff'>+190 Atq</span></span></div></label>" +
                    "<label class='inv-slot'><input type='radio' name='e_wep' id='e_wep_maliketh' value='maliketh' onchange='updateUIStyles()'><div class='slot-content'><span class='slot-title'>Hoja Negra de Maliketh</span><span class='slot-stats'><span class='stat-buff'>+230 Atq</span></span></div></label>" +

                    "<label class='inv-slot'><input type='radio' name='e_arm' id='e_arm_tree' value='tree' checked onchange='updateUIStyles()'><div class='slot-content'><span class='slot-title'>Set de Árbol Crisol</span><span class='slot-stats'><span class='stat-buff'>+135 Def</span></span></div></label>" +
                    "<label class='inv-slot'><input type='radio' name='e_arm' id='e_arm_maliketh' value='maliketh' onchange='updateUIStyles()'><div class='slot-content'><span class='slot-title'>Armadura de Maliketh</span><span class='slot-stats'><span class='stat-buff'>+125 Def</span></span></div></label>" +

                    "<label class='inv-slot' style='grid-column: 1 / -1;'><input type='checkbox' id='e_tal_blood' checked onchange='updateUIStyles()'><div class='slot-content'><span class='slot-title'>Exultación de Sangre</span><span class='slot-stats'><span class='stat-buff'>+90 Atq</span></span></div></label>" +
                    "</div>" +

                    "<div class='stats-panel'>" +
                    "<div class='stat-row'><span class='stat-label'>PS MAX</span><div class='stat-bar-bg'><div class='stat-bar-fill fill-hp' id='e_hp_bar'></div></div><span class='stat-value' id='e_hp_val'>0</span></div>" +
                    "<div class='stat-row'><span class='stat-label'>ATAQUE</span><div class='stat-bar-bg'><div class='stat-bar-fill fill-atk' id='e_atk_bar'></div></div><span class='stat-value' id='e_atk_val'>0</span></div>" +
                    "<div class='stat-row'><span class='stat-label'>DEFENSA</span><div class='stat-bar-bg'><div class='stat-bar-fill fill-def' id='e_def_bar'></div></div><span class='stat-value' id='e_def_val'>0</span></div>" +
                    "</div>" +
                    "</div>" +

                    "<div class='control-center'>" +
                    "<button class='btn-execute' id='btnExecute' onclick='triggerSimulation()'>Iniciar Combate a Muerte</button>" +
                    "</div>" +
                    "</div>" +

                    "<div class='overlay-results' id='overlayResults'>" +
                    "<div class='results-card'>" +
                    "<h2 class='res-title' id='resTitle'>Analisis Completado</h2>" +
                    "<div class='res-log' id='resLog'></div>" +
                    "<button class='btn-execute' style='padding: 12px 35px; font-size: 15px; border-width: 1px;' onclick='location.reload()'>Restablecer Entorno</button>" +
                    "</div>" +
                    "</div>" +

                    "<script>" +
                    "function updateUIStyles() {" +
                    "  document.querySelectorAll('.inv-slot').forEach(el => {" +
                    "    const input = el.querySelector('input');" +
                    "    if(input && input.checked) el.classList.add('active-slot');" +
                    "    else el.classList.remove('active-slot');" +
                    "  });" +
                    "  " +
                    "  let p_hp = 800, p_atk = 25, p_def = 15;" +
                    "  let e_hp = 1200, e_atk = 35, e_def = 20;" +
                    "  " +
                    "  if(document.getElementById('p_wep_rivers').checked) p_atk += 185;" +
                    "  if(document.getElementById('p_wep_moonveil').checked) p_atk += 165;" +
                    "  if(document.getElementById('p_wep_grafted').checked) p_atk += 210;" +
                    "  " +
                    "  if(document.getElementById('p_arm_radahn').checked) p_def += 145;" +
                    "  if(document.getElementById('p_arm_crucible').checked) p_def += 130;" +
                    "  if(document.getElementById('p_arm_wolf').checked) p_def += 95;" +
                    "  " +
                    "  if(document.getElementById('p_tal_favor').checked) { p_hp += 350; p_def += 25; }" +
                    "  if(document.getElementById('p_tal_radagon').checked) { p_atk += 80; p_hp += 250; p_def -= 40; }" +
                    "  " +
                    "  if(document.getElementById('e_wep_blasphemous').checked) e_atk += 190;" +
                    "  if(document.getElementById('e_wep_maliketh').checked) e_atk += 230;" +
                    "  " +
                    "  if(document.getElementById('e_arm_tree').checked) e_def += 135;" +
                    "  if(document.getElementById('e_arm_maliketh').checked) e_def += 125;" +
                    "  " +
                    "  if(document.getElementById('e_tal_blood').checked) e_atk += 90;" +
                    "  " +
                    "  document.getElementById('p_hp_bar').style.width = Math.min((p_hp/2000)*100, 100) + '%';" +
                    "  document.getElementById('p_atk_bar').style.width = Math.min((p_atk/400)*100, 100) + '%';" +
                    "  document.getElementById('p_def_bar').style.width = Math.min((p_def/250)*100, 100) + '%';" +
                    "  document.getElementById('p_hp_val').innerText = p_hp;" +
                    "  document.getElementById('p_atk_val').innerText = p_atk;" +
                    "  document.getElementById('p_def_val').innerText = p_def;" +
                    "  " +
                    "  document.getElementById('e_hp_bar').style.width = Math.min((e_hp/2000)*100, 100) + '%';" +
                    "  document.getElementById('e_atk_bar').style.width = Math.min((e_atk/400)*100, 100) + '%';" +
                    "  document.getElementById('e_def_bar').style.width = Math.min((e_def/250)*100, 100) + '%';" +
                    "  document.getElementById('e_hp_val').innerText = e_hp;" +
                    "  document.getElementById('e_atk_val').innerText = e_atk;" +
                    "  document.getElementById('e_def_val').innerText = e_def;" +
                    "}" +

                    "updateUIStyles();" +

                    "function triggerSimulation() {" +
                    "  const btn = document.getElementById('btnExecute');" +
                    "  btn.disabled = true; btn.innerText = 'PROCESANDO...';" +
                    "  " +
                    "  let pw = 'rivers';" +
                    "  if(document.getElementById('p_wep_moonveil').checked) pw = 'moonveil';" +
                    "  if(document.getElementById('p_wep_grafted').checked) pw = 'grafted';" +
                    "  " +
                    "  let pa = 'radahn';" +
                    "  if(document.getElementById('p_arm_crucible').checked) pa = 'crucible';" +
                    "  if(document.getElementById('p_arm_wolf').checked) pa = 'wolf';" +
                    "  " +
                    "  let ew = 'blasphemous';" +
                    "  if(document.getElementById('e_wep_maliketh').checked) ew = 'maliketh';" +
                    "  " +
                    "  let ea = 'tree';" +
                    "  if(document.getElementById('e_arm_maliketh').checked) ea = 'maliketh';" +
                    "  " +
                    "  const p_tf = document.getElementById('p_tal_favor').checked;" +
                    "  const p_tr = document.getElementById('p_tal_radagon').checked;" +
                    "  const e_tb = document.getElementById('e_tal_blood').checked;" +

                    "  const query = `pw=${pw}&pa=${pa}&ptf=${p_tf}&ptr=${p_tr}&ew=${ew}&ea=${ea}&etb=${e_tb}`;" +
                    "  " +
                    "  fetch(`/api/resolve?${query}`)" +
                    "    .then(res => res.json())" +
                    "    .then(data => {" +
                    "      document.body.classList.add('combat-active');" +
                    "      const pSkel = document.getElementById('pSkeleton');" +
                    "      const eSkel = document.getElementById('eSkeleton');" +
                    "      " +
                    "      pSkel.classList.add('attacking');" +
                    "      eSkel.classList.add('attacking');" +

                    "      setTimeout(() => {" +
                    "        if(data.winner === 'player') {" +
                    "          eSkel.classList.add('staggered');" +
                    "          document.getElementById('e_hp_bar').classList.add('hp-depleted');" +
                    "          document.getElementById('e_hp_val').innerText = '0';" +
                    "        } else if(data.winner === 'enemy') {" +
                    "          pSkel.classList.add('staggered');" +
                    "          document.getElementById('p_hp_bar').classList.add('hp-depleted');" +
                    "          document.getElementById('p_hp_val').innerText = '0';" +
                    "        } else {" +
                    "          pSkel.classList.add('staggered'); eSkel.classList.add('staggered');" +
                    "        }" +
                    "      }, 450);" +

                    "      setTimeout(() => {" +
                    "        document.getElementById('resTitle').innerText = data.winner === 'player' ? 'LEYENDA FORJADA' : (data.winner === 'enemy' ? 'HAS PERECIDO' : 'EMPATE TACTICO');" +
                    "        document.getElementById('resTitle').style.color = data.winner === 'player' ? 'var(--accent-gold)' : (data.winner === 'enemy' ? 'var(--accent-red)' : 'var(--text-muted)');" +
                    "        document.getElementById('resLog').innerHTML = data.log;" +
                    "        document.getElementById('overlayResults').classList.add('show');" +
                    "        document.body.classList.remove('combat-active');" +
                    "      }, 2600);" +
                    "    })" +
                    "    .catch(err => {" +
                    "       alert('Error de comunicacion. Verifica la terminal del servidor Java.');" +
                    "       console.error(err);" +
                    "       btn.disabled = false; btn.innerText = 'REINTENTAR';" +
                    "    });" +
                    "}" +
                    "</script>" +
                    "</body></html>";

            t.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
            OutputStream os = t.getResponseBody();
            os.write(html.getBytes(StandardCharsets.UTF_8));
            os.close();
        }
    }

    static class CombatEngineHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String query = t.getRequestURI().getQuery();

            // 1. Instanciacion Core
            Fighter player = new BaseTarnished();
            Fighter enemy = new AbyssalInvader();

            if (query != null) {
                // Modificadores del Jugador
                if (query.contains("pw=rivers")) player = new RiversOfBloodKatana(player);
                else if (query.contains("pw=moonveil")) player = new MoonveilKatana(player);
                else if (query.contains("pw=grafted")) player = new GraftedBladeGreatsword(player);

                if (query.contains("pa=radahn")) player = new RadahnArmorSet(player);
                else if (query.contains("pa=crucible")) player = new CrucibleKnightSet(player);
                else if (query.contains("pa=wolf")) player = new RagingWolfSet(player);

                if (query.contains("ptf=true")) player = new ErdtreeFavorTalisman(player);
                if (query.contains("ptr=true")) player = new RadagonsSoreseal(player);

                // Modificadores Simetricos del Enemigo
                if (query.contains("ew=blasphemous")) enemy = new BlasphemousGreatsword(enemy);
                else if (query.contains("ew=maliketh")) enemy = new MalikethsBlackBlade(enemy);

                if (query.contains("ea=tree")) enemy = new TreeCrucibleSet(enemy);
                else if (query.contains("ea=maliketh")) enemy = new MalikethsArmorSet(enemy);

                if (query.contains("etb=true")) enemy = new LordOfBloodExultation(enemy);
            }

            // 2. Logica Matematica Exacta
            int pNetDmg = Math.max(0, player.getAttackPower() - enemy.getDefense());
            int eNetDmg = Math.max(0, enemy.getAttackPower() - player.getDefense());

            int hitsToKillEnemy = (pNetDmg > 0) ? (int) Math.ceil((double) enemy.getMaxHealth() / pNetDmg) : 9999;
            int hitsToKillPlayer = (eNetDmg > 0) ? (int) Math.ceil((double) player.getMaxHealth() / eNetDmg) : 9999;

            String winner;
            StringBuilder log = new StringBuilder();
            log.append("<span class='log-highlight'>[SISTEMA DE ANÁLISIS]</span> Evaluación estructural del Patrón Decorator...<br><br>");
            log.append(String.format("- Protagonista  => Ataque: <span class='log-highlight'>%d</span> | Defensa: <span class='log-highlight'>%d</span> | HP: <span class='log-highlight'>%d</span><br>", player.getAttackPower(), player.getDefense(), player.getMaxHealth()));
            log.append(String.format("- Antagonista   => Ataque: <span class='log-highlight'>%d</span> | Defensa: <span class='log-highlight'>%d</span> | HP: <span class='log-highlight'>%d</span><br><br>", enemy.getAttackPower(), enemy.getDefense(), enemy.getMaxHealth()));

            log.append(String.format("► Daño neto por golpe del Protagonista: %d pts.<br>", pNetDmg));
            log.append(String.format("► Daño neto por golpe del Invasor: %d pts.<br><br>", eNetDmg));

            if (hitsToKillEnemy < hitsToKillPlayer) {
                winner = "player";
                log.append(String.format("<span class='log-highlight'>[VICTORIA RECLAMADA]</span> El Protagonista abrumo las defensas enemigas requiriendo solo %d impactos letales.", hitsToKillEnemy));
            } else if (hitsToKillPlayer < hitsToKillEnemy) {
                winner = "enemy";
                log.append(String.format("<span style='color:#f85149; font-weight:bold;'>[DERROTA FATAL]</span> El Antagonista penetro las defensas en %d impactos letales.", hitsToKillPlayer));
            } else {
                winner = "draw";
                log.append("<span class='log-highlight'>[EMPATE DE ACERO]</span> Resistencia absoluta. Ninguna entidad logró superar la mitigación de daño del oponente.");
            }

            // 3. Serializacion Segura JSON
            String json = String.format(
                    "{" +
                            "\"p\": {\"atk\": %d, \"def\": %d, \"hp\": %d}," +
                            "\"e\": {\"atk\": %d, \"def\": %d, \"hp\": %d}," +
                            "\"winner\": \"%s\"," +
                            "\"log\": \"%s\"" +
                            "}",
                    player.getAttackPower(), player.getDefense(), player.getMaxHealth(),
                    enemy.getAttackPower(), enemy.getDefense(), enemy.getMaxHealth(),
                    winner,
                    log.toString()
            );

            t.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            t.sendResponseHeaders(200, bytes.length);
            OutputStream os = t.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }
}