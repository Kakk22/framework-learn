package com.cyf.codegen.processor;

import com.cyf.codegen.context.ProcessingEnvironmentHolder;
import com.cyf.codegen.register.CodeGenProcessorRegistry;
import com.cyf.codegen.spi.CodeGenProcessor;
import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * @author 陈一锋
 * @date 2022/9/12 10:08 下午
 */
@AutoService(Processor.class)
public class MyCodeGenProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        annotations.forEach(an -> {
            Set<? extends Element> typeElements = roundEnv.getElementsAnnotatedWith(an);
            Set<TypeElement> types = ElementFilter.typesIn(typeElements);
            for (TypeElement typeElement : types){
                CodeGenProcessor codeGenProcessor = CodeGenProcessorRegistry.find(
                        an.getQualifiedName().toString());
                try {
                    codeGenProcessor.generate(typeElement,roundEnv);
                } catch (Exception e) {
                    ProcessingEnvironmentHolder.getEnvironment().getMessager().printMessage(Diagnostic.Kind.ERROR,"代码生成异常:" + e.getMessage());
                }
            }

        });
        return false;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        ProcessingEnvironmentHolder.setEnvironment(processingEnv);
        CodeGenProcessorRegistry.initProcessors();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return CodeGenProcessorRegistry.getSupportedAnnotations();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
